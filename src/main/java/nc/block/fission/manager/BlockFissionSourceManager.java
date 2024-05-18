package nc.block.fission.manager;

import nc.tile.fission.TileFissionSource;
import nc.tile.fission.manager.TileFissionSourceManager;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.*;

import javax.annotation.Nullable;

public class BlockFissionSourceManager extends BlockFissionManager<TileFissionSourceManager, TileFissionSource> {
	
	public BlockFissionSourceManager() {
		super(TileFissionSourceManager.class);
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int metadata) {
		return new TileFissionSourceManager();
	}
	
	@Override
	public boolean canConnectRedstone(IBlockState state, IBlockAccess world, BlockPos pos, @Nullable EnumFacing side) {
		return side != null;
	}
}
