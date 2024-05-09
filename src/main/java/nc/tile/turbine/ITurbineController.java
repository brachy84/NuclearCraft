package nc.tile.turbine;

import nc.multiblock.turbine.Turbine;
import nc.network.multiblock.TurbineUpdatePacket;
import nc.tile.info.TileContainerInfo;
import nc.tile.multiblock.ILogicMultiblockController;
import net.minecraft.tileentity.TileEntity;

public interface ITurbineController<CONTROLLER extends TileEntity & ITurbineController<CONTROLLER>> extends ITurbinePart, ILogicMultiblockController<Turbine, ITurbinePart, TurbineUpdatePacket, CONTROLLER, TileContainerInfo<CONTROLLER>> {
	
	boolean isRenderer();
	
	void setIsRenderer(boolean isRenderer);
}
