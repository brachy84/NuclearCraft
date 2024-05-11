package nc.render;

import nc.block.fluid.NCBlockFluid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.color.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import javax.annotation.Nullable;

public class ColorRenderer {
	
	public static class FluidBlockColor implements IBlockColor {
		
		final NCBlockFluid fluidBlock;
		
		public FluidBlockColor(NCBlockFluid fluidBlock) {
			this.fluidBlock = fluidBlock;
		}
		
		@Override
		public int colorMultiplier(IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos, int tintIndex) {
			if (tintIndex == 0) {
				return fluidBlock.fluid.getColor();
			}
			return 0xFFFFFF;
		}
	}
	
	public static class FluidItemBlockColor implements IItemColor {
		
		final NCBlockFluid fluidBlock;
		
		public FluidItemBlockColor(NCBlockFluid fluidBlock) {
			this.fluidBlock = fluidBlock;
		}
		
		@Override
		public int colorMultiplier(ItemStack stack, int tintIndex) {
			if (tintIndex == 0) {
				return fluidBlock.fluid.getColor();
			}
			return 0xFFFFFF;
		}
	}
}
