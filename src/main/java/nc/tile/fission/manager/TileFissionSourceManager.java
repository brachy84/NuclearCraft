package nc.tile.fission.manager;

import nc.tile.fission.TileFissionSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileFissionSourceManager extends TileFissionManager<TileFissionSourceManager, TileFissionSource> {
	
	public TileFissionSourceManager() {
		super(TileFissionSourceManager.class);
	}
	
	@Override
	public int[] weakSidesToCheck(World worldIn, BlockPos posIn) {
		return new int[] {2, 3, 4, 5};
	}
	
	@Override
	public String getManagerType() {
		return "fissionSourceManager";
	}
	
	@Override
	public Class<TileFissionSource> getListenerClass() {
		return TileFissionSource.class;
	}
	
	@Override
	public boolean isManagerActive() {
		return getIsRedstonePowered();
	}
}
