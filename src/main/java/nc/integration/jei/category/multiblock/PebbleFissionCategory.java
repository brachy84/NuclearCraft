package nc.integration.jei.category.multiblock;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import nc.Global;
import nc.integration.jei.JEIHelper.RecipeItemMapper;
import nc.integration.jei.NCJEI.IJEIHandler;
import nc.integration.jei.category.JEIMachineCategory;
import nc.integration.jei.wrapper.JEIRecipeWrapperImpl;
import nc.recipe.IngredientSorption;
import nc.util.Lang;

public class PebbleFissionCategory extends JEIMachineCategory<JEIRecipeWrapperImpl.PebbleFission> {
	
	public PebbleFissionCategory(IGuiHelper guiHelper, IJEIHandler<JEIRecipeWrapperImpl.PebbleFission> handler) {
		super(guiHelper, handler, "pebble_fission_chamber", 47, 30, 90, 26);
	}
	
	@Override
	public void setRecipe(IRecipeLayout recipeLayout, JEIRecipeWrapperImpl.PebbleFission recipeWrapper, IIngredients ingredients) {
		super.setRecipe(recipeLayout, recipeWrapper, ingredients);
		
		RecipeItemMapper itemMapper = new RecipeItemMapper();
		itemMapper.map(IngredientSorption.INPUT, 0, 0, 56 - backPosX, 35 - backPosY);
		itemMapper.map(IngredientSorption.OUTPUT, 0, 1, 116 - backPosX, 35 - backPosY);
		itemMapper.mapItemsTo(recipeLayout.getItemStacks(), ingredients);
	}
	
	@Override
	public String getTitle() {
		return Lang.localize(Global.MOD_ID + ".multiblock_gui.pebble_fission.jei_name");
	}
}