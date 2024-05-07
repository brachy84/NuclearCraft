package nc.container.multiblock.port;

import nc.multiblock.fission.FissionReactor;
import nc.multiblock.fission.FissionReactorLogic;
import nc.network.tile.multiblock.port.FluidPortUpdatePacket;
import nc.recipe.BasicRecipeHandler;
import nc.recipe.NCRecipes;
import nc.tile.TileContainerInfo;
import nc.tile.fission.IFissionPart;
import nc.tile.fission.TileSaltFissionVessel;
import nc.tile.fission.port.TileFissionVesselPort;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;

public class ContainerFissionVesselPort extends ContainerPort<FissionReactor, FissionReactorLogic, IFissionPart, TileFissionVesselPort, TileSaltFissionVessel, FluidPortUpdatePacket, TileContainerInfo<TileFissionVesselPort>> {
	
	public ContainerFissionVesselPort(EntityPlayer player, TileFissionVesselPort tile) {
		super(player, tile);
		
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				addSlotToContainer(new Slot(player.inventory, j + 9 * i + 9, 8 + 18 * j, 84 + 18 * i));
			}
		}
		
		for (int i = 0; i < 9; ++i) {
			addSlotToContainer(new Slot(player.inventory, i, 8 + 18 * i, 142));
		}
	}
	
	@Override
	protected BasicRecipeHandler getRecipeHandler() {
		return NCRecipes.salt_fission;
	}
}
