package nc.tile.fission.port;

import nc.recipe.NCRecipes;
import nc.tile.fission.TileFissionIrradiator;

public class TileFissionIrradiatorPort extends TileFissionItemPort<TileFissionIrradiatorPort, TileFissionIrradiator> {

	public TileFissionIrradiatorPort() {
		super("fission_irradiator_port", TileFissionIrradiatorPort.class, NCRecipes.fission_irradiator);
	}
}
