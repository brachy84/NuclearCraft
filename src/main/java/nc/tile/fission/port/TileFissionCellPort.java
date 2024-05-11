package nc.tile.fission.port;

import nc.recipe.NCRecipes;
import nc.tile.fission.TileSolidFissionCell;

public class TileFissionCellPort extends TileFissionItemPort<TileFissionCellPort, TileSolidFissionCell> {
	
	public TileFissionCellPort() {
		super("fission_cell_port", TileFissionCellPort.class, NCRecipes.solid_fission);
	}
}
