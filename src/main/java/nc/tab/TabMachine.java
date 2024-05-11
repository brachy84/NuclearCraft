package nc.tab;

import nc.init.NCBlocks;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

import static nc.config.NCConfig.register_processor;

public class TabMachine extends CreativeTabs {
	
	public TabMachine() {
		super("nuclearcraft.machine");
	}
	
	@Override
	public ItemStack createIcon() {
		return new ItemStack(register_processor[1] ? NCBlocks.manufactory : NCBlocks.machine_interface);
	}
}
