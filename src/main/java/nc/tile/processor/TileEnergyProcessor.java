package nc.tile.processor;

import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import li.cil.oc.api.network.SimpleComponent;
import nc.ModCheck;
import nc.handler.TileInfoHandler;
import nc.network.tile.processor.EnergyProcessorUpdatePacket;
import nc.recipe.*;
import nc.tile.energy.ITileEnergy;
import nc.tile.energyFluid.TileEnergyFluidSidedInventory;
import nc.tile.fluid.ITileFluid;
import nc.tile.internal.energy.EnergyConnection;
import nc.tile.internal.fluid.*;
import nc.tile.internal.inventory.InventoryConnection;
import nc.tile.inventory.ITileInventory;
import nc.tile.processor.info.ProcessorContainerInfo;
import nc.util.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fluids.capability.*;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.items.*;

import javax.annotation.*;
import java.util.*;

@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "opencomputers")
public abstract class TileEnergyProcessor<TILE extends TileEnergyProcessor<TILE, INFO>, INFO extends ProcessorContainerInfo<TILE, EnergyProcessorUpdatePacket, INFO>> extends TileEnergyFluidSidedInventory implements IProcessor<TILE, EnergyProcessorUpdatePacket, INFO>, SimpleComponent {
	
	protected INFO info;
	
	protected @Nonnull NonNullList<ItemStack> consumedStacks = null;
	protected @Nonnull List<Tank> consumedTanks = null;
	
	public double baseProcessTime, baseProcessPower, baseProcessRadiation;
	
	public double time, resetTime;
	public boolean isProcessing, canProcessInputs, hasConsumed;
	
	protected BasicRecipeHandler recipeHandler;
	protected RecipeInfo<BasicRecipe> recipeInfo = null;
	
	protected final Set<EntityPlayer> updatePacketListeners = new ObjectOpenHashSet<>();
	
	protected final HandlerPair[] adjacentHandlers = new HandlerPair[6];
	
	/**
	 * Don't use this constructor!
	 */
	protected TileEnergyProcessor() {
		super("energy_processor", 0, null, 1, null, 1, null, null);
	}
	
	protected TileEnergyProcessor(String name) {
		this(name, TileInfoHandler.getProcessorContainerInfo(name));
	}
	
	private TileEnergyProcessor(String name, INFO info) {
		this(name, info, ITileInventory.inventoryConnectionAll(info.defaultItemSorptions()), info.getEnergyCapacity(1D, 1D), ITileEnergy.energyConnectionAll(info.defaultEnergyConnection()), info.defaultTankCapacities(), NCRecipes.getValidFluids(info.recipeHandlerName), ITileFluid.fluidConnectionAll(info.defaultTankSorptions()));
	}
	
	private TileEnergyProcessor(String name, INFO info, @Nonnull InventoryConnection[] inventoryConnections, long capacity, @Nonnull EnergyConnection[] energyConnections, @Nonnull IntList fluidCapacity, List<Set<String>> allowedFluids, @Nonnull FluidConnection[] fluidConnections) {
		super(name, info.getInventorySize(), inventoryConnections, capacity, energyConnections, fluidCapacity, allowedFluids, fluidConnections);
		initFromInfo(info, false);
	}
	
	protected void initFromInfo(INFO info, boolean superConstructor) {
		this.info = info;
		
		if (superConstructor) {
			long capacity = info.getEnergyCapacity(1D, 1D);
			initTileEnergy(capacity, NCMath.toInt(capacity), ITileEnergy.energyConnectionAll(info.defaultEnergyConnection()));
			initTileEnergyFluid(info.defaultTankCapacities(), NCRecipes.getValidFluids(info.recipeHandlerName), ITileFluid.fluidConnectionAll(info.defaultTankSorptions()));
			initTileEnergyFluidInventory(info.name, info.getInventorySize(), ITileInventory.inventoryConnectionAll(info.defaultItemSorptions()));
		}
		
		baseProcessTime = info.defaultProcessTime;
		baseProcessPower = info.defaultProcessPower;
		
		setInputTanksSeparated(info.fluidInputSize > 1);
		
		consumedStacks = info.getConsumedStacks();
		consumedTanks = info.getConsumedTanks();
		
		recipeHandler = info.getRecipeHandler();
	}
	
	// Ticking
	
	@Override
	public void onLoad() {
		super.onLoad();
		if (!world.isRemote) {
			updateAdjacentHandlers();
			refreshAll();
		}
	}
	
	@Override
	public void update() {
		if (!world.isRemote) {
			onTick();
		}
	}
	
	@Override
	public @Nullable HandlerPair[] getAdjacentHandlers() {
		return adjacentHandlers;
	}
	
	@Override
	public void updateAdjacentHandlers() {
		for (int i = 0; i < 6; ++i) {
			EnumFacing side = EnumFacing.VALUES[i], opposite = side.getOpposite();
			TileEntity tile = world.getTileEntity(pos.offset(side));
			IItemHandler itemHandler = getCapabilitySafe(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, tile, opposite);
			IFluidHandler fluidHandler = getCapabilitySafe(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, tile, opposite);
			adjacentHandlers[i] = itemHandler == null && fluidHandler == null ? null : new HandlerPair(itemHandler, fluidHandler);
		}
	}
	
	@Override
	public void refreshDirty() {
		IProcessor.super.refreshDirty();
		refreshEnergyCapacity();
	}
	
	public void refreshEnergyCapacity() {}
	
	// IProcessor
	
	@Override
	public INFO getContainerInfo() {
		return info;
	}
	
	@Override
	public BasicRecipeHandler getRecipeHandler() {
		return recipeHandler;
	}
	
	@Override
	public RecipeInfo<BasicRecipe> getRecipeInfo() {
		return recipeInfo;
	}
	
	@Override
	public void setRecipeInfo(RecipeInfo<BasicRecipe> recipeInfo) {
		this.recipeInfo = recipeInfo;
	}
	
	@Override
	public void setRecipeStats(@Nullable BasicRecipe recipe) {
		IProcessor.super.setRecipeStats(recipe);
		baseProcessRadiation = recipe == null ? 0D : recipe.getBaseProcessRadiation();
	}
	
	@Override
	public @Nonnull NonNullList<ItemStack> getConsumedStacks() {
		return consumedStacks;
	}
	
	@Override
	public @Nonnull List<Tank> getConsumedTanks() {
		return consumedTanks;
	}
	
	@Override
	public double getBaseProcessTime() {
		return baseProcessTime;
	}
	
	@Override
	public void setBaseProcessTime(double baseProcessTime) {
		this.baseProcessTime = baseProcessTime;
	}
	
	@Override
	public double getBaseProcessPower() {
		return baseProcessPower;
	}
	
	@Override
	public void setBaseProcessPower(double baseProcessPower) {
		this.baseProcessPower = baseProcessPower;
	}
	
	@Override
	public double getCurrentTime() {
		return time;
	}
	
	@Override
	public void setCurrentTime(double time) {
		this.time = time;
	}
	
	@Override
	public double getResetTime() {
		return resetTime;
	}
	
	@Override
	public void setResetTime(double resetTime) {
		this.resetTime = resetTime;
	}
	
	@Override
	public boolean getIsProcessing() {
		return isProcessing;
	}
	
	@Override
	public void setIsProcessing(boolean isProcessing) {
		this.isProcessing = isProcessing;
	}
	
	@Override
	public boolean getCanProcessInputs() {
		return canProcessInputs;
	}
	
	@Override
	public void setCanProcessInputs(boolean canProcessInputs) {
		this.canProcessInputs = canProcessInputs;
	}
	
	@Override
	public boolean getHasConsumed() {
		return hasConsumed;
	}
	
	@Override
	public void setHasConsumed(boolean hasConsumed) {
		this.hasConsumed = hasConsumed;
	}
	
	@Override
	public double getSpeedMultiplier() {
		return 1D;
	}
	
	@Override
	public double getPowerMultiplier() {
		return 1D;
	}
	
	@Override
	public boolean isHalted() {
		return getRedstoneControl() && getIsRedstonePowered();
	}
	
	@Override
	public boolean readyToProcess() {
		return IProcessor.super.readyToProcess() && hasSufficientEnergy();
	}
	
	// Needed for Galacticraft
	protected long getMaxEnergyModified() {
		return ModCheck.galacticraftLoaded() ? Math.max(0L, getMaxEnergyStoredLong() - 16L) : getMaxEnergyStoredLong();
	}
	
	public boolean hasSufficientEnergy() {
		if (time <= resetTime) {
			long processEnergy = getProcessEnergy(), maxEnergy = getMaxEnergyModified();
			if (processEnergy >= maxEnergy) {
				return getEnergyStored() >= maxEnergy;
			}
			else {
				return getEnergyStored() >= processEnergy;
			}
		}
		else {
			return getEnergyStored() >= getProcessPower();
		}
	}
	
	@Override
	public void process() {
		getEnergyStorage().changeEnergyStored(-getProcessPower());
		getRadiationSource().setRadiationLevel(baseProcessRadiation * getSpeedMultiplier());
		IProcessor.super.process();
	}
	
	// IC2 Tiers
	
	@Override
	public int getSinkTier() {
		return 10;
	}
	
	@Override
	public int getSourceTier() {
		return 1;
	}
	
	// ITileInventory
	
	@Override
	public void markDirty() {
		refreshDirty();
		super.markDirty();
	}
	
	@Override
	public boolean hasConfigurableInventoryConnections() {
		return true;
	}
	
	// ITileFluid
	
	@Override
	public boolean hasConfigurableFluidConnections() {
		return true;
	}
	
	// IGui
	
	@Override
	public Set<EntityPlayer> getTileUpdatePacketListeners() {
		return updatePacketListeners;
	}
	
	@Override
	public EnergyProcessorUpdatePacket getTileUpdatePacket() {
		return new EnergyProcessorUpdatePacket(pos, isProcessing, time, baseProcessTime, getTanks(), baseProcessPower, getEnergyStoredLong());
	}
	
	@Override
	public void onTileUpdatePacket(EnergyProcessorUpdatePacket message) {
		IProcessor.super.onTileUpdatePacket(message);
		baseProcessPower = message.baseProcessPower;
		getEnergyStorage().setEnergyStored(message.energyStored);
	}
	
	// IMultitoolLogic
	
	@Override
	public boolean onUseMultitool(ItemStack multitool, EntityPlayer player, World world, EnumFacing facing, float hitX, float hitY, float hitZ) {
		NBTTagCompound nbt = NBTHelper.getStackNBT(multitool, "ncMultitool");
		if (nbt != null) {
			if (player.isSneaking()) {
				NBTTagCompound config = new NBTTagCompound();
				EnumFacing dir = getFacingHorizontal();
				config.setString("infoName", info.name);
				if (hasConfigurableInventoryConnections()) {
					config.setTag("inventoryConnections", writeInventoryConnectionsDirectional(new NBTTagCompound(), dir));
					config.setTag("slotSettings", writeSlotSettings(new NBTTagCompound()));
				}
				if (hasConfigurableFluidConnections()) {
					config.setTag("fluidConnections", writeFluidConnectionsDirectional(new NBTTagCompound(), dir));
					config.setTag("tankSettings", writeTankSettings(new NBTTagCompound()));
				}
				config.setBoolean("alternateComparator", getAlternateComparator());
				config.setBoolean("redstoneControl", getRedstoneControl());
				player.sendMessage(new TextComponentString(Lang.localize("info.nuclearcraft.multitool.save_processor_config", getDisplayName().getUnformattedText())));
				nbt.setTag("processorConfig", config);
				return true;
			}
			else if (nbt.hasKey("processorConfig", 10)) {
				NBTTagCompound config = nbt.getCompoundTag("processorConfig");
				if (info.name.equals(config.getString("infoName"))) {
					EnumFacing dir = getFacingHorizontal();
					if (hasConfigurableInventoryConnections()) {
						readInventoryConnectionsDirectional(config.getCompoundTag("inventoryConnections"), dir);
						readSlotSettings(config.getCompoundTag("slotSettings"));
					}
					if (hasConfigurableFluidConnections()) {
						readFluidConnectionsDirectional(config.getCompoundTag("fluidConnections"), dir);
						readTankSettings(config.getCompoundTag("tankSettings"));
					}
					setAlternateComparator(config.getBoolean("alternateComparator"));
					setRedstoneControl(config.getBoolean("redstoneControl"));
					player.sendMessage(new TextComponentString(Lang.localize("info.nuclearcraft.multitool.load_processor_config", getDisplayName().getUnformattedText())));
					return true;
				}
				else {
				
				}
			}
		}
		return IProcessor.super.onUseMultitool(multitool, player, world, facing, hitX, hitY, hitZ);
	}
	
	// NBT
	
	@Override
	public NBTTagCompound writeAll(NBTTagCompound nbt) {
		nbt.setString("infoName", info.name);
		super.writeAll(nbt);
		writeProcessorNBT(nbt);
		return nbt;
	}
	
	@Override
	public void readAll(NBTTagCompound nbt) {
		if (info == null && nbt.hasKey("infoName")) {
			initFromInfo(TileInfoHandler.getProcessorContainerInfo(nbt.getString("infoName")), true);
		}
		super.readAll(nbt);
		readProcessorNBT(nbt);
	}
	
	// OpenComputers
	
	@Override
	@Optional.Method(modid = "opencomputers")
	public String getComponentName() {
		return getContainerInfo().ocComponentName;
	}
}