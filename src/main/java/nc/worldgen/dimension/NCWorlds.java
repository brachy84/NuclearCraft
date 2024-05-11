package nc.worldgen.dimension;

import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;

import static nc.config.NCConfig.*;

public class NCWorlds {
	
	public static DimensionType wastelandDimType;
	
	public static void registerDimensions() {
		if (wasteland_dimension_gen) {
			wastelandDimType = DimensionType.register("nc_wasteland", "_nc_wasteland", wasteland_dimension, WorldProviderWasteland.class, false);
			DimensionManager.registerDimension(wasteland_dimension, wastelandDimType);
		}
	}
}
