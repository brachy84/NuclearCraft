package nc.init;

import nc.Global;
import nc.item.IInfoItem;
import nc.item.armor.ItemHazmatSuit;
import nc.item.armor.NCItemArmor;
import nc.tab.NCTabs;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.util.EnumHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static nc.config.NCConfig.*;

public class NCArmor {
	
	public static final ArmorMaterial BORON = armorMaterial(Global.MOD_ID, "boron", 0, armor_boron, SoundEvents.ITEM_ARMOR_EQUIP_IRON, new ItemStack(NCBlocks.ore, 1, 5));
	public static final ArmorMaterial TOUGH = armorMaterial(Global.MOD_ID, "tough", 1, armor_tough, SoundEvents.ITEM_ARMOR_EQUIP_IRON, new ItemStack(NCItems.alloy, 1, 1));
	public static final ArmorMaterial HARD_CARBON = armorMaterial(Global.MOD_ID, "hard_carbon", 2, armor_hard_carbon, SoundEvents.ITEM_ARMOR_EQUIP_IRON, new ItemStack(NCItems.alloy, 1, 2));
	public static final ArmorMaterial BORON_NITRIDE = armorMaterial(Global.MOD_ID, "boron_nitride", 3, armor_boron_nitride, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, new ItemStack(NCItems.gem, 1, 1));
	public static final ArmorMaterial HAZMAT = armorMaterial(Global.MOD_ID, "hazmat", 4, armor_hazmat, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, new ItemStack(Items.LEATHER));
	
	public static Item helm_boron;
	public static Item chest_boron;
	public static Item legs_boron;
	public static Item boots_boron;
	
	public static Item helm_tough;
	public static Item chest_tough;
	public static Item legs_tough;
	public static Item boots_tough;
	
	public static Item helm_hard_carbon;
	public static Item chest_hard_carbon;
	public static Item legs_hard_carbon;
	public static Item boots_hard_carbon;
	
	public static Item helm_boron_nitride;
	public static Item chest_boron_nitride;
	public static Item legs_boron_nitride;
	public static Item boots_boron_nitride;
	
	public static Item helm_hazmat;
	public static Item chest_hazmat;
	public static Item legs_hazmat;
	public static Item boots_hazmat;

	public static List<ItemRegistrationInfo<?>> registrationList = new ArrayList<>();
	
	public static void init() {
		if (register_armor[0]) {
			helm_boron = addWithName(Global.MOD_ID, "helm_boron", new NCItemArmor(BORON, 1, EntityEquipmentSlot.HEAD, TextFormatting.GRAY), NCTabs.misc);
			chest_boron = addWithName(Global.MOD_ID, "chest_boron", new NCItemArmor(BORON, 1, EntityEquipmentSlot.CHEST, TextFormatting.GRAY), NCTabs.misc);
			legs_boron = addWithName(Global.MOD_ID, "legs_boron", new NCItemArmor(BORON, 2, EntityEquipmentSlot.LEGS, TextFormatting.GRAY), NCTabs.misc);
			boots_boron = addWithName(Global.MOD_ID, "boots_boron", new NCItemArmor(BORON, 1, EntityEquipmentSlot.FEET, TextFormatting.GRAY), NCTabs.misc);
		}
		
		if (register_armor[1]) {
			helm_tough = addWithName(Global.MOD_ID, "helm_tough", new NCItemArmor(TOUGH, 1, EntityEquipmentSlot.HEAD, TextFormatting.DARK_PURPLE), NCTabs.misc);
			chest_tough = addWithName(Global.MOD_ID, "chest_tough", new NCItemArmor(TOUGH, 1, EntityEquipmentSlot.CHEST, TextFormatting.DARK_PURPLE), NCTabs.misc);
			legs_tough = addWithName(Global.MOD_ID, "legs_tough", new NCItemArmor(TOUGH, 2, EntityEquipmentSlot.LEGS, TextFormatting.DARK_PURPLE), NCTabs.misc);
			boots_tough = addWithName(Global.MOD_ID, "boots_tough", new NCItemArmor(TOUGH, 1, EntityEquipmentSlot.FEET, TextFormatting.DARK_PURPLE), NCTabs.misc);
		}
		
		if (register_armor[2]) {
			helm_hard_carbon = addWithName(Global.MOD_ID, "helm_hard_carbon", new NCItemArmor(HARD_CARBON, 1, EntityEquipmentSlot.HEAD, TextFormatting.BLUE), NCTabs.misc);
			chest_hard_carbon = addWithName(Global.MOD_ID, "chest_hard_carbon", new NCItemArmor(HARD_CARBON, 1, EntityEquipmentSlot.CHEST, TextFormatting.BLUE), NCTabs.misc);
			legs_hard_carbon = addWithName(Global.MOD_ID, "legs_hard_carbon", new NCItemArmor(HARD_CARBON, 2, EntityEquipmentSlot.LEGS, TextFormatting.BLUE), NCTabs.misc);
			boots_hard_carbon = addWithName(Global.MOD_ID, "boots_hard_carbon", new NCItemArmor(HARD_CARBON, 1, EntityEquipmentSlot.FEET, TextFormatting.BLUE), NCTabs.misc);
		}
		
		if (register_armor[3]) {
			helm_boron_nitride = addWithName(Global.MOD_ID, "helm_boron_nitride", new NCItemArmor(BORON_NITRIDE, 1, EntityEquipmentSlot.HEAD, TextFormatting.GREEN), NCTabs.misc);
			chest_boron_nitride = addWithName(Global.MOD_ID, "chest_boron_nitride", new NCItemArmor(BORON_NITRIDE, 1, EntityEquipmentSlot.CHEST, TextFormatting.GREEN), NCTabs.misc);
			legs_boron_nitride = addWithName(Global.MOD_ID, "legs_boron_nitride", new NCItemArmor(BORON_NITRIDE, 2, EntityEquipmentSlot.LEGS, TextFormatting.GREEN), NCTabs.misc);
			boots_boron_nitride = addWithName(Global.MOD_ID, "boots_boron_nitride", new NCItemArmor(BORON_NITRIDE, 1, EntityEquipmentSlot.FEET, TextFormatting.GREEN), NCTabs.misc);
		}
		
		helm_hazmat = addWithName(Global.MOD_ID, "helm_hazmat", new ItemHazmatSuit(HAZMAT, 1, EntityEquipmentSlot.HEAD, 0.2D, TextFormatting.YELLOW), NCTabs.radiation);
		chest_hazmat = addWithName(Global.MOD_ID, "chest_hazmat", new ItemHazmatSuit(HAZMAT, 1, EntityEquipmentSlot.CHEST, 0.4D, TextFormatting.YELLOW), NCTabs.radiation);
		legs_hazmat = addWithName(Global.MOD_ID, "legs_hazmat", new ItemHazmatSuit(HAZMAT, 2, EntityEquipmentSlot.LEGS, 0.2D, TextFormatting.YELLOW), NCTabs.radiation);
		boots_hazmat = addWithName(Global.MOD_ID, "boots_hazmat", new ItemHazmatSuit(HAZMAT, 1, EntityEquipmentSlot.FEET, 0.2D, TextFormatting.YELLOW), NCTabs.radiation);
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
	
	public static ArmorMaterial armorMaterial(String modId, String name, int id, int[] durability, SoundEvent equipSound, ItemStack repairStack) {
		return EnumHelper.addArmorMaterial(name, modId + ":" + name, armor_durability[id], new int[] {durability[0], durability[1], durability[2], durability[3]}, armor_enchantability[id], equipSound, (float) armor_toughness[id]).setRepairItem(repairStack);
	}
}
