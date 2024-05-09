package nc.container.multiblock.port;

import nc.multiblock.fission.FissionReactor;
import nc.multiblock.fission.FissionReactorLogic;
import nc.network.tile.multiblock.port.FluidPortUpdatePacket;
import nc.recipe.BasicRecipeHandler;
import nc.recipe.NCRecipes;
import nc.tile.info.TileContainerInfo;
import nc.tile.fission.IFissionPart;
import nc.tile.fission.TileSaltFissionHeater;
import nc.tile.fission.port.TileFissionHeaterPort;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;

public class ContainerFissionHeaterPort extends ContainerPort<FissionReactor, FissionReactorLogic, IFissionPart, TileFissionHeaterPort, TileSaltFissionHeater, FluidPortUpdatePacket, TileContainerInfo<TileFissionHeaterPort>> {
	
	public ContainerFissionHeaterPort(EntityPlayer player, TileFissionHeaterPort tile) {
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
		return NCRecipes.coolant_heater;
	}
}
