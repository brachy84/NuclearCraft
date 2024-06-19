package nc.multiblock.hx;

import com.google.common.collect.Lists;
import nc.Global;
import nc.multiblock.*;
import nc.network.multiblock.HeatExchangerUpdatePacket;
import nc.tile.hx.*;
import nc.tile.multiblock.TilePartAbstract.SyncReason;
import net.minecraft.nbt.NBTTagCompound;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

import static nc.config.NCConfig.*;

public class HeatExchangerLogic extends MultiblockLogic<HeatExchanger, HeatExchangerLogic, IHeatExchangerPart> implements IPacketMultiblockLogic<HeatExchanger, HeatExchangerLogic, IHeatExchangerPart, HeatExchangerUpdatePacket> {
	
	public HeatExchangerLogic(HeatExchanger exchanger) {
		super(exchanger);
	}
	
	public HeatExchangerLogic(HeatExchangerLogic oldLogic) {
		super(oldLogic);
	}
	
	@Override
	public String getID() {
		return "heat_exchanger";
	}
	
	protected HeatExchanger getExchanger() {
		return multiblock;
	}
	
	// Multiblock Size Limits
	
	@Override
	public int getMinimumInteriorLength() {
		return heat_exchanger_min_size;
	}
	
	@Override
	public int getMaximumInteriorLength() {
		return heat_exchanger_max_size;
	}
	
	// Multiblock Methods
	
	@Override
	public void onMachineAssembled() {
		onExchangerFormed();
	}
	
	@Override
	public void onMachineRestored() {
		onExchangerFormed();
	}
	
	protected void onExchangerFormed() {
		getExchanger().setIsHeatExchangerOn();
		
		if (!getWorld().isRemote) {
			/*for (TileHeatExchangerTube tube : tubes) {
				tube.updateFlowDir();
			}
			for (TileCondenserTube condenserTube : condenserTubes) {
				condenserTube.updateAdjacentTemperatures();
			}*/
			
			getExchanger().updateHeatExchangerStats();
		}
	}
	
	@Override
	public void onMachinePaused() {
		onExchangerBroken();
	}
	
	@Override
	public void onMachineDisassembled() {
		onExchangerBroken();
	}
	
	public void onExchangerBroken() {
		getExchanger().isHeatExchangerOn = false;
		if (getExchanger().controller != null) {
			getExchanger().controller.setActivity(false);
		}
		getExchanger().fractionOfTubesActive = getExchanger().efficiency = getExchanger().maxEfficiency = 0D;
	}
	
	@Override
	public boolean isMachineWhole() {
		return !containsBlacklistedPart();
	}
	
	public static final List<Pair<Class<? extends IHeatExchangerPart>, String>> EXCHANGER_PART_BLACKLIST = Lists.newArrayList(Pair.of(TileCondenserTube.class, Global.MOD_ID + ".multiblock_validation.heat_exchanger.prohibit_condenser_tubes"));
	
	@Override
	public List<Pair<Class<? extends IHeatExchangerPart>, String>> getPartBlacklist() {
		return EXCHANGER_PART_BLACKLIST;
	}
	
	@Override
	public void onAssimilate(HeatExchanger assimilated) {
		/*if (getExchanger().isAssembled()) {
			onExchangerFormed();
		}
		else {
			onExchangerBroken();
		}*/
	}
	
	@Override
	public void onAssimilated(HeatExchanger assimilator) {}
	
	// Server
	
	@Override
	public boolean onUpdateServer() {
		return false;
	}
	
	// Client
	
	@Override
	public void onUpdateClient() {}
	
	// NBT
	
	@Override
	public void writeToLogicTag(NBTTagCompound data, SyncReason syncReason) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void readFromLogicTag(NBTTagCompound data, SyncReason syncReason) {
		// TODO Auto-generated method stub
		
	}
	
	// Packets
	
	@Override
	public HeatExchangerUpdatePacket getMultiblockUpdatePacket() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onMultiblockUpdatePacket(HeatExchangerUpdatePacket message) {
		// TODO Auto-generated method stub
		
	}
	
	// Clear Material
	
	@Override
	public void clearAllMaterial() {
		// TODO Auto-generated method stub
		
	}
}
