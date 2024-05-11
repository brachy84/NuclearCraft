package nc.tile.fission;

import nc.multiblock.fission.FissionReactor;
import nc.network.multiblock.FissionUpdatePacket;
import nc.tile.TileContainerInfo;
import nc.tile.multiblock.ILogicMultiblockController;
import net.minecraft.tileentity.TileEntity;

import java.util.Iterator;

public interface IFissionController<CONTROLLER extends TileEntity & IFissionController<CONTROLLER>> extends IFissionPart, ILogicMultiblockController<FissionReactor, IFissionPart, FissionUpdatePacket, CONTROLLER, TileContainerInfo<CONTROLLER>> {
	
	void doMeltdown(Iterator<IFissionController<?>> controllerIterator);
}
