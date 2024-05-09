package nc.container.multiblock.controller;

import nc.multiblock.turbine.Turbine;
import nc.network.multiblock.TurbineUpdatePacket;
import nc.tile.info.TileContainerInfo;
import nc.tile.turbine.*;
import net.minecraft.entity.player.EntityPlayer;

public class ContainerTurbineController extends ContainerMultiblockController<Turbine, ITurbinePart, TurbineUpdatePacket, TileTurbineController, TileContainerInfo<TileTurbineController>> {
	
	public ContainerTurbineController(EntityPlayer player, TileTurbineController controller) {
		super(player, controller);
	}
}
