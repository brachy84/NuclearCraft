package nc.recipe.processor;

import net.minecraft.item.crafting.FurnaceRecipes;

public class ElectricFurnaceRecipes extends BasicProcessorRecipeHandler {

	public ElectricFurnaceRecipes() {
		super("electric_furnace", 1, 0, 1, 0);
	}
	
	@Override
	public void addRecipes() {}

	@Override
	public void postInit() {
		FurnaceRecipes.instance().getSmeltingList().forEach((k, v) -> addRecipe(k, v, 1D, 1D));
	}
}
