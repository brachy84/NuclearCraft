#loader nc_preinit

import mods.nuclearcraft.ProcessorBuilderHelper.standardSlot;
import mods.nuclearcraft.ProcessorBuilderHelper.bigSlot;

mods.nuclearcraft.UpgradableEnergyProcessorBuilder("glowstone_aggregator")
	.setParticles(["reddust", "depthsuspend"] as string[])
	.setDefaultProcessTime(600.0)
	.setDefaultProcessPower(100.0)
	.setItemInputSlots([standardSlot(56, 35)] as int[][])
	.setItemOutputSlots([bigSlot(112, 31)] as int[][])
	.buildAndRegister();
