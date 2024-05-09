package nc.tile.fission;

import java.util.Iterator;

import nc.multiblock.fission.FissionReactor;
import nc.network.multiblock.FissionUpdatePacket;
import nc.tile.info.TileContainerInfo;
import nc.tile.multiblock.ILogicMultiblockController;
import net.minecraft.tileentity.TileEntity;

public interface IFissionController<CONTROLLER extends TileEntity & IFissionController<CONTROLLER>> extends IFissionPart, ILogicMultiblockController<FissionReactor, IFissionPart, FissionUpdatePacket, CONTROLLER, TileContainerInfo<CONTROLLER>> {
	
	void doMeltdown(Iterator<IFissionController<?>> controllerIterator);
}
