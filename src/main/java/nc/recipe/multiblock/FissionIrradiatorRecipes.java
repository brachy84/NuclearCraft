package nc.recipe.multiblock;

import com.google.common.collect.Lists;
import nc.radiation.RadSources;
import nc.recipe.BasicRecipeHandler;

import java.util.List;

import static nc.config.NCConfig.*;

public class FissionIrradiatorRecipes extends BasicRecipeHandler {
	
	public FissionIrradiatorRecipes() {
		super("fission_irradiator", 1, 0, 1, 0);
	}
	
	@Override
	public void addRecipes() {
		addRecipe(Lists.newArrayList("ingotThorium", "dustThorium"), "dustTBP", 160000L, fission_irradiator_heat_per_flux[0], fission_irradiator_efficiency[0], 0L, -1L, RadSources.THORIUM);
		addRecipe(Lists.newArrayList("ingotTBP", "dustTBP"), "dustProtactinium233", 2720000L, fission_irradiator_heat_per_flux[1], fission_irradiator_efficiency[1], 0L, -1L, RadSources.TBP);
		addRecipe(Lists.newArrayList("ingotBismuth", "dustBismuth"), "dustPolonium", 1920000L, fission_irradiator_heat_per_flux[2], fission_irradiator_efficiency[2], 0L, -1L, RadSources.BISMUTH);
	}
	
	@Override
	protected List<Object> fixedExtras(List<Object> extras) {
		ExtrasFixer fixer = new ExtrasFixer(extras);
		fixer.add(Long.class, 1L);
		fixer.add(Double.class, 0D);
		fixer.add(Double.class, 0D);
		fixer.add(Long.class, 0L);
		fixer.add(Long.class, -1L);
		fixer.add(Double.class, 0D);
		return fixer.fixed;
	}
}
