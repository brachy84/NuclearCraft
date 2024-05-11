package nc.recipe.multiblock;

import nc.recipe.BasicRecipeHandler;

import java.util.List;

import static nc.config.NCConfig.fission_emergency_cooling_multiplier;

public class FissionEmergencyCoolingRecipes extends BasicRecipeHandler {
	
	public FissionEmergencyCoolingRecipes() {
		super("fission_emergency_cooling", 0, 1, 0, 1);
	}
	
	@Override
	public void addRecipes() {
		addRecipe(fluidStack("emergency_coolant", 1), fluidStack("emergency_coolant_heated", 1), fission_emergency_cooling_multiplier);
	}
	
	@Override
	protected List<Object> fixedExtras(List<Object> extras) {
		ExtrasFixer fixer = new ExtrasFixer(extras);
		fixer.add(Double.class, 1D);
		return fixer.fixed;
	}
}
