package nc.block.tile.info;

import java.util.function.Supplier;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;

public class BlockSimpleTileInfo<TILE extends TileEntity> extends BlockTileInfo<TILE> {
	
	public BlockSimpleTileInfo(String modId, String name, Class<TILE> tileClass, Supplier<TILE> tileSupplier, CreativeTabs creativeTab) {
		super(modId, name, tileClass, tileSupplier, creativeTab);
	}
}
