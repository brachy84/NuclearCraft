package nc.enumm;

import net.minecraft.tileentity.TileEntity;

public interface ICoolingComponentEnum<T extends TileEntity> extends ITileEnum<T> {
	
	int getCooling();
}
