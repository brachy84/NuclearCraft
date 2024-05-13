package nc.tile.fluid;

import it.unimi.dsi.fastutil.ints.IntList;
import nc.tile.internal.fluid.FluidConnection;
import nc.tile.internal.inventory.InventoryConnection;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.*;
import java.util.*;

public abstract class TileFluidSidedInventory extends TileFluidInventory {
	
	public TileFluidSidedInventory(String name, int size, @Nonnull InventoryConnection[] inventoryConnections, int capacity, Set<String> allowedFluids, @Nonnull FluidConnection[] fluidConnections) {
		super(name, size, inventoryConnections, capacity, allowedFluids, fluidConnections);
	}
	
	public TileFluidSidedInventory(String name, int size, @Nonnull InventoryConnection[] inventoryConnections, @Nonnull IntList capacity, List<Set<String>> allowedFluids, @Nonnull FluidConnection[] fluidConnections) {
		super(name, size, inventoryConnections, capacity, allowedFluids, fluidConnections);
	}
	
	// Capability
	
	@Override
	public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing side) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			if (!getInventoryStacks().isEmpty() && hasInventorySideCapability(side)) {
				return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(getItemHandler(side));
			}
			return null;
		}
		return super.getCapability(capability, side);
	}
}
