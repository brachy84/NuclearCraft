package nc.item;

import nc.util.*;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.*;

import javax.annotation.Nullable;
import java.util.List;

public class NCItemMetaArray extends Item implements IInfoItem {
	
	public final String[] types;
	
	public final TextFormatting infoColor;
	private final String[][] tooltips;
	public String[][] info;
	
	public NCItemMetaArray(List<String> types, TextFormatting infoColor, String[]... tooltips) {
		setHasSubtypes(true);
		this.types = types.toArray(new String[0]);
		this.infoColor = infoColor;
		this.tooltips = tooltips;
	}
	
	public NCItemMetaArray(List<String> names, String[]... tooltips) {
		this(names, TextFormatting.AQUA, tooltips);
	}
	
	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (isInCreativeTab(tab)) {
			for (int i = 0; i < types.length; ++i) {
				items.add(new ItemStack(this, 1, i));
			}
		}
	}
	
	@Override
	public String getTranslationKey(ItemStack stack) {
		for (int i = 0; i < types.length; ++i) {
			if (StackHelper.getMetadata(stack) == i) {
				return getTranslationKey() + "." + types[i];
			}
		}
		return getTranslationKey() + "." + types[0];
	}
	
	@Override
	public void setInfo() {
		info = InfoHelper.buildInfo(getTranslationKey(), types, tooltips);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
		super.addInformation(stack, world, tooltip, flag);
		int meta = StackHelper.getMetadata(stack);
		if (info.length > meta) {
			InfoHelper.infoFull(tooltip, TextFormatting.RED, InfoHelper.EMPTY_ARRAY, infoColor, info[meta]);
		}
	}
	
	protected ActionResult<ItemStack> actionResult(boolean success, ItemStack stack) {
		return new ActionResult<>(success ? EnumActionResult.SUCCESS : EnumActionResult.FAIL, stack);
	}
}
