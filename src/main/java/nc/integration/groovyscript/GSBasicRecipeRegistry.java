package nc.integration.groovyscript;

import com.cleanroommc.groovyscript.api.GroovyBlacklist;
import com.cleanroommc.groovyscript.helper.Alias;
import com.cleanroommc.groovyscript.registry.VirtualizedRegistry;
import nc.recipe.*;
import nc.recipe.ingredient.*;
import nc.util.*;

import java.util.*;

public abstract class GSBasicRecipeRegistry extends VirtualizedRegistry<BasicRecipe> {
	
	@GroovyBlacklist
	protected final Lazy<BasicRecipeHandler> recipeHandler;
	
	public GSBasicRecipeRegistry(String name) {
		super(Alias.generateOf(name));
		this.recipeHandler = new Lazy<>(() -> NCRecipes.getHandler(name));
	}
	
	@GroovyBlacklist
	protected BasicRecipeHandler getRecipeHandler() {
		return recipeHandler.get();
	}
	
	@GroovyBlacklist
	@Override
	public void onReload() {
	
	}
	
	@GroovyBlacklist
	public void onReloadSynchronized() {
		BasicRecipeHandler recipeHandler = getRecipeHandler();
		removeScripted().forEach(recipeHandler::removeRecipe);
		restoreFromBackup().forEach(recipeHandler::addRecipe);
	}
	
	@GroovyBlacklist
	protected void addRecipeInternal(Object... objects) {
		BasicRecipeHandler recipeHandler = getRecipeHandler();
		BasicRecipe recipe = recipeHandler.buildRecipe(objects);
		addScripted(recipe);
		recipeHandler.addRecipe(recipe);
	}
	
	@GroovyBlacklist
	protected void removeRecipeWithInputInternal(Object... inputs) {
		List<Object> inputList = Arrays.asList(inputs);
		BasicRecipeHandler recipeHandler = getRecipeHandler();
		List<IItemIngredient> itemIngredients = StreamHelper.map(inputList.subList(0, recipeHandler.itemInputSize), RecipeHelper::buildItemIngredient);
		List<IFluidIngredient> fluidIngredients = StreamHelper.map(inputList.subList(recipeHandler.itemInputSize, recipeHandler.itemInputSize + recipeHandler.fluidInputSize), RecipeHelper::buildFluidIngredient);
		BasicRecipe recipe = recipeHandler.getRecipeFromIngredients(itemIngredients, fluidIngredients);
		while (recipeHandler.removeRecipe(recipe)) {
			addBackup(recipe);
			recipe = recipeHandler.getRecipeFromIngredients(itemIngredients, fluidIngredients);
		}
	}
	
	@GroovyBlacklist
	protected void removeRecipeWithOutputInternal(Object... outputs) {
		List<Object> outputList = Arrays.asList(outputs);
		BasicRecipeHandler recipeHandler = getRecipeHandler();
		List<IItemIngredient> itemProducts = StreamHelper.map(outputList.subList(0, recipeHandler.itemOutputSize), RecipeHelper::buildItemIngredient);
		List<IFluidIngredient> fluidProducts = StreamHelper.map(outputList.subList(recipeHandler.itemOutputSize, recipeHandler.itemOutputSize + recipeHandler.fluidOutputSize), RecipeHelper::buildFluidIngredient);
		BasicRecipe recipe = recipeHandler.getRecipeFromProducts(itemProducts, fluidProducts);
		while (recipeHandler.removeRecipe(recipe)) {
			addBackup(recipe);
			recipe = recipeHandler.getRecipeFromProducts(itemProducts, fluidProducts);
		}
	}
	
	@GroovyBlacklist
	protected void removeAllRecipesInternal() {
		BasicRecipeHandler recipeHandler = getRecipeHandler();
		recipeHandler.getRecipeList().forEach(this::addBackup);
		recipeHandler.removeAllRecipes();
	}
	
	public String getName() {
		return super.getName();
	}
	
	public List<BasicRecipe> getRecipeList() {
		return getRecipeHandler().getRecipeList();
	}
	
	public int getItemInputSize() {
		return getRecipeHandler().getItemInputSize();
	}
	
	public int getFluidInputSize() {
		return getRecipeHandler().getFluidInputSize();
	}
	
	public int getItemOutputSize() {
		return getRecipeHandler().getItemOutputSize();
	}
	
	public int getFluidOutputSize() {
		return getRecipeHandler().getFluidOutputSize();
	}
	
	public boolean isShapeless() {
		return getRecipeHandler().isShapeless();
	}
}
