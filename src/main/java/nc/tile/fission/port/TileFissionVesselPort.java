package nc.tile.fission.port;

import nc.recipe.NCRecipes;
import nc.tile.TileContainerInfo;
import nc.tile.fission.TileSaltFissionVessel;

import static nc.util.FluidStackHelper.INGOT_BLOCK_VOLUME;

public class TileFissionVesselPort extends TileFissionFluidPort<TileFissionVesselPort, TileSaltFissionVessel> {

	public TileFissionVesselPort() {
		super("fission_vessel_port", TileFissionVesselPort.class, INGOT_BLOCK_VOLUME, NCRecipes.salt_fission.validFluids.get(0), NCRecipes.salt_fission);
	}
}
