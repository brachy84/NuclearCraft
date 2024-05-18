package nc.util;

import nc.tile.internal.fluid.Tank;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

import java.util.List;

public class OCHelper {
	
	public static Object[] posInfo(long posLong) {
		return posInfo(BlockPos.fromLong(posLong));
	}
	
	public static Object[] posInfo(BlockPos pos) {
		return new Object[] {pos.getX(), pos.getY(), pos.getZ()};
	}
	
	public static Object[] stackInfo(ItemStack stack) {
		return stack.isEmpty() ? new Object[] {0, "null"} : new Object[] {stack.getCount(), StackHelper.stackName(stack)};
	}
	
	public static Object[] stackInfoArray(List<ItemStack> stack) {
		return StreamHelper.map(stack, OCHelper::stackInfo).toArray();
	}
	
	public static Object[] tankInfo(Tank tank) {
		return tank.isEmpty() ? new Object[] {0, "null"} : new Object[] {tank.getFluidAmount(), tank.getFluidName()};
	}
	
	public static Object[] tankInfoArray(List<Tank> stack) {
		return StreamHelper.map(stack, OCHelper::tankInfo).toArray();
	}
}
