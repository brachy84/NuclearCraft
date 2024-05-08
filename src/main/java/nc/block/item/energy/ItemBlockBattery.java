package nc.block.item.energy;

import nc.block.battery.BlockBattery;
import nc.tile.internal.energy.EnergyConnection;
import net.minecraft.block.Block;

public class ItemBlockBattery extends ItemBlockEnergy {
	
	public ItemBlockBattery(Block block, long capacity, int maxTransfer, int energyTier, String... tooltip) {
		super(block, capacity, maxTransfer, energyTier, EnergyConnection.BOTH, tooltip);
	}
	
	public ItemBlockBattery(BlockBattery block, String... tooltip) {
		this(block, block.type.getCapacity(), block.type.getMaxTransfer(), block.type.getEnergyTier(), tooltip);
	}
}
