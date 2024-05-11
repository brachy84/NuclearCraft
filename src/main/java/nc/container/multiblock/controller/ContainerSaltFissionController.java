package nc.container.multiblock.controller;

import nc.multiblock.fission.FissionReactor;
import nc.network.multiblock.FissionUpdatePacket;
import nc.tile.fission.*;
import nc.tile.TileContainerInfo;
import net.minecraft.entity.player.EntityPlayer;

public class ContainerSaltFissionController extends ContainerMultiblockController<FissionReactor, IFissionPart, FissionUpdatePacket, TileSaltFissionController, TileContainerInfo<TileSaltFissionController>> {
	
	public ContainerSaltFissionController(EntityPlayer player, TileSaltFissionController controller) {
		super(player, controller);
	}
}
