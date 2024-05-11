package nc.enumm;

import net.minecraft.tileentity.TileEntity;

public interface ITileEnum<T extends TileEntity> {
	
	Class<? extends T> getTileClass();
}
