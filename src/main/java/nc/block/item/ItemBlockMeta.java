package nc.block.item;

import nc.block.IBlockMeta;
import nc.enumm.IMetaEnum;
import nc.util.*;
import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.*;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.*;

import javax.annotation.Nullable;
import java.util.List;

public class ItemBlockMeta<T extends Enum<T> & IStringSerializable & IMetaEnum> extends ItemBlock {
	
	public final Class<T> enumm;
	public final T[] values;
	
	public final TextFormatting[] fixedColors;
	public final TextFormatting infoColor;
	public final String[][] fixedInfo, info;
	
	public <V extends Block & IBlockMeta<T>> ItemBlockMeta(V block, TextFormatting[] fixedColors, String[][] fixedTooltips, TextFormatting infoColor, String[]... tooltips) {
		super(block);
		enumm = block.getEnumClass();
		values = block.getValues();
		setMaxDamage(0);
		setHasSubtypes(true);
		this.fixedColors = fixedColors;
		fixedInfo = InfoHelper.buildFixedInfo(block.getTranslationKey(), enumm, fixedTooltips);
		this.infoColor = infoColor;
		info = InfoHelper.buildInfo(block.getTranslationKey(), enumm, tooltips);
	}
	
	public <V extends Block & IBlockMeta<T>> ItemBlockMeta(V block, TextFormatting fixedColor, String[][] fixedTooltips, TextFormatting infoColor, String[]... tooltips) {
		this(block, new TextFormatting[] {fixedColor}, fixedTooltips, infoColor, tooltips);
	}
	
	public <V extends Block & IBlockMeta<T>> ItemBlockMeta(V block, TextFormatting infoColor, String[]... tooltips) {
		this(block, TextFormatting.RED, InfoHelper.EMPTY_ARRAYS, infoColor, tooltips);
	}
	
	public <V extends Block & IBlockMeta<T>> ItemBlockMeta(V block, String[]... tooltips) {
		this(block, TextFormatting.RED, InfoHelper.EMPTY_ARRAYS, TextFormatting.AQUA, tooltips);
	}
	
	@Override
	public String getTranslationKey(ItemStack stack) {
		return getTranslationKey() + "." + ((IBlockMeta<?>) block).getMetaName(stack);
	}
	
	@Override
	public int getMetadata(int damage) {
		return damage;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
		super.addInformation(stack, world, tooltip, flag);
		int meta = StackHelper.getMetadata(stack);
		if (fixedInfo.length > meta && info.length > meta) {
			if (fixedColors.length == 1) {
				InfoHelper.infoFull(tooltip, fixedColors[0], fixedInfo[meta], infoColor, info[meta]);
			}
			else {
				InfoHelper.infoFull(tooltip, fixedColors, fixedInfo[meta], infoColor, info[meta]);
			}
		}
	}
}
