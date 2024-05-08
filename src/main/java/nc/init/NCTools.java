package nc.init;

import nc.Global;
import nc.item.IInfoItem;
import nc.item.tool.*;
import nc.tab.NCTabs;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.util.EnumHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static nc.config.NCConfig.*;

public class NCTools {
	
	public static final ToolMaterial BORON = toolMaterial(Global.MOD_ID, "boron", 0, new ItemStack(NCItems.ingot, 1, 5));
	public static final ToolMaterial SPAXELHOE_BORON = toolMaterial(Global.MOD_ID, "spaxelhoe_boron", 1, new ItemStack(NCItems.ingot, 1, 5));

	public static final ToolMaterial TOUGH = toolMaterial(Global.MOD_ID, "tough", 2, new ItemStack(NCItems.alloy, 1, 1));
	public static final ToolMaterial SPAXELHOE_TOUGH = toolMaterial(Global.MOD_ID, "spaxelhoe_tough", 3, new ItemStack(NCItems.alloy, 1, 1));

	public static final ToolMaterial HARD_CARBON = toolMaterial(Global.MOD_ID, "hard_carbon", 4, new ItemStack(NCItems.alloy, 1, 2));
	public static final ToolMaterial SPAXELHOE_HARD_CARBON = toolMaterial(Global.MOD_ID, "spaxelhoe_hard_carbon", 5, new ItemStack(NCItems.alloy, 1, 2));

	public static final ToolMaterial BORON_NITRIDE = toolMaterial(Global.MOD_ID, "boron_nitride", 6, new ItemStack(NCItems.gem, 1, 1));
	public static final ToolMaterial SPAXELHOE_BORON_NITRIDE = toolMaterial(Global.MOD_ID, "spaxelhoe_boron_nitride", 7, new ItemStack(NCItems.gem, 1, 1));
	
	public static Item sword_boron;
	public static Item pickaxe_boron;
	public static Item shovel_boron;
	public static Item axe_boron;
	public static Item hoe_boron;
	public static Item spaxelhoe_boron;
	
	public static Item sword_tough;
	public static Item pickaxe_tough;
	public static Item shovel_tough;
	public static Item axe_tough;
	public static Item hoe_tough;
	public static Item spaxelhoe_tough;
	
	public static Item sword_hard_carbon;
	public static Item pickaxe_hard_carbon;
	public static Item shovel_hard_carbon;
	public static Item axe_hard_carbon;
	public static Item hoe_hard_carbon;
	public static Item spaxelhoe_hard_carbon;
	
	public static Item sword_boron_nitride;
	public static Item pickaxe_boron_nitride;
	public static Item shovel_boron_nitride;
	public static Item axe_boron_nitride;
	public static Item hoe_boron_nitride;
	public static Item spaxelhoe_boron_nitride;

	public static List<ItemRegistrationInfo<?>> registrationList = new ArrayList<>();
	
	public static void init() {
		if (register_tool[0]) {
			sword_boron = addWithName(Global.MOD_ID, "sword_boron", new NCSword(BORON, TextFormatting.GRAY), NCTabs.misc);
			pickaxe_boron = addWithName(Global.MOD_ID, "pickaxe_boron", new NCPickaxe(BORON, TextFormatting.GRAY), NCTabs.misc);
			shovel_boron = addWithName(Global.MOD_ID, "shovel_boron", new NCShovel(BORON, TextFormatting.GRAY), NCTabs.misc);
			axe_boron = addWithName(Global.MOD_ID, "axe_boron", new NCAxe(BORON, TextFormatting.GRAY), NCTabs.misc);
			hoe_boron = addWithName(Global.MOD_ID, "hoe_boron", new NCHoe(BORON, TextFormatting.GRAY), NCTabs.misc);
			spaxelhoe_boron = addWithName(Global.MOD_ID, "spaxelhoe_boron", new NCSpaxelhoe(SPAXELHOE_BORON, TextFormatting.GRAY), NCTabs.misc);
		}
		
		if (register_tool[1]) {
			sword_tough = addWithName(Global.MOD_ID, "sword_tough", new NCSword(TOUGH, TextFormatting.DARK_PURPLE), NCTabs.misc);
			pickaxe_tough = addWithName(Global.MOD_ID, "pickaxe_tough", new NCPickaxe(TOUGH, TextFormatting.DARK_PURPLE), NCTabs.misc);
			shovel_tough = addWithName(Global.MOD_ID, "shovel_tough", new NCShovel(TOUGH, TextFormatting.DARK_PURPLE), NCTabs.misc);
			axe_tough = addWithName(Global.MOD_ID, "axe_tough", new NCAxe(TOUGH, TextFormatting.DARK_PURPLE), NCTabs.misc);
			hoe_tough = addWithName(Global.MOD_ID, "hoe_tough", new NCHoe(TOUGH, TextFormatting.DARK_PURPLE), NCTabs.misc);
			spaxelhoe_tough = addWithName(Global.MOD_ID, "spaxelhoe_tough", new NCSpaxelhoe(SPAXELHOE_TOUGH, TextFormatting.DARK_PURPLE), NCTabs.misc);
		}
		
		if (register_tool[2]) {
			sword_hard_carbon = addWithName(Global.MOD_ID, "sword_hard_carbon", new NCSword(HARD_CARBON, TextFormatting.BLUE), NCTabs.misc);
			pickaxe_hard_carbon = addWithName(Global.MOD_ID, "pickaxe_hard_carbon", new NCPickaxe(HARD_CARBON, TextFormatting.BLUE), NCTabs.misc);
			shovel_hard_carbon = addWithName(Global.MOD_ID, "shovel_hard_carbon", new NCShovel(HARD_CARBON, TextFormatting.BLUE), NCTabs.misc);
			axe_hard_carbon = addWithName(Global.MOD_ID, "axe_hard_carbon", new NCAxe(HARD_CARBON, TextFormatting.BLUE), NCTabs.misc);
			hoe_hard_carbon = addWithName(Global.MOD_ID, "hoe_hard_carbon", new NCHoe(HARD_CARBON, TextFormatting.BLUE), NCTabs.misc);
			spaxelhoe_hard_carbon = addWithName(Global.MOD_ID, "spaxelhoe_hard_carbon", new NCSpaxelhoe(SPAXELHOE_HARD_CARBON, TextFormatting.BLUE), NCTabs.misc);
		}
		
		if (register_tool[3]) {
			sword_boron_nitride = addWithName(Global.MOD_ID, "sword_boron_nitride", new NCSword(BORON_NITRIDE, TextFormatting.GREEN), NCTabs.misc);
			pickaxe_boron_nitride = addWithName(Global.MOD_ID, "pickaxe_boron_nitride", new NCPickaxe(BORON_NITRIDE, TextFormatting.GREEN), NCTabs.misc);
			shovel_boron_nitride = addWithName(Global.MOD_ID, "shovel_boron_nitride", new NCShovel(BORON_NITRIDE, TextFormatting.GREEN), NCTabs.misc);
			axe_boron_nitride = addWithName(Global.MOD_ID, "axe_boron_nitride", new NCAxe(BORON_NITRIDE, TextFormatting.GREEN), NCTabs.misc);
			hoe_boron_nitride = addWithName(Global.MOD_ID, "hoe_boron_nitride", new NCHoe(BORON_NITRIDE, TextFormatting.GREEN), NCTabs.misc);
			spaxelhoe_boron_nitride = addWithName(Global.MOD_ID, "spaxelhoe_boron_nitride", new NCSpaxelhoe(SPAXELHOE_BORON_NITRIDE, TextFormatting.GREEN), NCTabs.misc);
		}
	}

	public static void register() {
		for (ItemRegistrationInfo<?> registration : registrationList) {
			registration.registerItem.run();
		}
	}

	public static void registerRenders() {
		for (ItemRegistrationInfo<?> registration : registrationList) {
			registration.registerRender.run();
		}
	}

	// Factory

	public static <T extends Item & IInfoItem> T addWithName(String modId, String name, T item, Consumer<ItemRegistrationInfo<T>> registerItem, Consumer<ItemRegistrationInfo<T>> registerRender) {
		item = NCItems.withName(modId, name, item);
		registrationList.add(new ItemRegistrationInfo<>(modId, name, item, registerItem, registerRender));
		return item;
	}

	public static <T extends Item & IInfoItem> T addWithName(String modId, String name, T item, CreativeTabs tab) {
		return addWithName(modId, name, item, x -> NCItems.registerItem(x.item, tab), x -> NCItems.registerRender(x.item));
	}

	// Auxiliary
	
	public static ToolMaterial toolMaterial(String modId, String name, int id, ItemStack repairStack) {
		return EnumHelper.addToolMaterial(modId + ":" + name, tool_mining_level[id], tool_durability[id], (float) tool_speed[id], (float) tool_attack_damage[id], tool_enchantability[id]).setRepairItem(repairStack);
	}
}
