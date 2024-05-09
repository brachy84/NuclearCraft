package nc.tile.processor.info;

import java.util.List;
import java.util.function.Supplier;

import nc.block.tile.info.BlockTileInfo;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;

public class ProcessorBlockInfo<TILE extends TileEntity> extends BlockTileInfo<TILE> {
	
	public final List<String> particles;
	
	public ProcessorBlockInfo(String modId, String name, Class<TILE> tileClass, Supplier<TILE> tileSupplier, CreativeTabs creativeTab, List<String> particles) {
		super(modId, name, tileClass, tileSupplier, creativeTab);
		this.particles = particles;
	}
}
