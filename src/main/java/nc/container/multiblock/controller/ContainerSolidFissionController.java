package nc.container.multiblock.controller;

import nc.multiblock.fission.FissionReactor;
import nc.network.multiblock.FissionUpdatePacket;
import nc.tile.fission.*;
import nc.tile.TileContainerInfo;
import net.minecraft.entity.player.EntityPlayer;

public class ContainerSolidFissionController extends ContainerMultiblockController<FissionReactor, IFissionPart, FissionUpdatePacket, TileSolidFissionController, TileContainerInfo<TileSolidFissionController>> {
	
	public ContainerSolidFissionController(EntityPlayer player, TileSolidFissionController controller) {
		super(player, controller);
	}
}
