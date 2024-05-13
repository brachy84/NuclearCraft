package nc.recipe;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import it.unimi.dsi.fastutil.ints.*;
import it.unimi.dsi.fastutil.objects.*;
import nc.integration.crafttweaker.*;
import nc.integration.gtce.GTCERecipeHelper;
import nc.recipe.ingredient.*;
import nc.tile.internal.fluid.Tank;
import nc.util.*;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.Optional;
import org.apache.commons.lang3.tuple.Pair;
import stanhebben.zenscript.annotations.*;

import javax.annotation.*;
import java.util.*;
import java.util.function.*;

import static nc.config.NCConfig.*;

@ZenClass("mods.nuclearcraft.BasicRecipeHandler")
@ZenRegister
public abstract class BasicRecipeHandler extends AbstractRecipeHandler<BasicRecipe> {
	
	protected final String name;
	protected final int itemInputSize, fluidInputSize, itemOutputSize, fluidOutputSize;
	protected final boolean isShapeless;
	
	public List<Set<String>> validFluids = null;
	
	public BasicRecipeHandler(@Nonnull String name, int itemInputSize, int fluidInputSize, int itemOutputSize, int fluidOutputSize) {
		this(name, itemInputSize, fluidInputSize, itemOutputSize, fluidOutputSize, true);
	}
	
	public BasicRecipeHandler(@Nonnull String name, int itemInputSize, int fluidInputSize, int itemOutputSize, int fluidOutputSize, boolean isShapeless) {
		this.name = name;
		this.itemInputSize = itemInputSize;
		this.fluidInputSize = fluidInputSize;
		this.itemOutputSize = itemOutputSize;
		this.fluidOutputSize = fluidOutputSize;
		this.isShapeless = isShapeless;
		addRecipes();
	}
	
	@Override
	public void addRecipe(Object... objects) {
		List<Object> itemInputs = new ArrayList<>(), fluidInputs = new ArrayList<>(), itemOutputs = new ArrayList<>(), fluidOutputs = new ArrayList<>(), extras = new ArrayList<>();
		for (int i = 0; i < objects.length; ++i) {
			Object object = objects[i];
			if (i < itemInputSize) {
				itemInputs.add(object);
			}
			else if (i < itemInputSize + fluidInputSize) {
				fluidInputs.add(object);
			}
			else if (i < itemInputSize + fluidInputSize + itemOutputSize) {
				itemOutputs.add(object);
			}
			else if (i < itemInputSize + fluidInputSize + itemOutputSize + fluidOutputSize) {
				fluidOutputs.add(object);
			}
			else {
				extras.add(object);
			}
		}
		BasicRecipe recipe = buildRecipe(itemInputs, fluidInputs, itemOutputs, fluidOutputs, extras, isShapeless);
		addRecipe(factor_recipes ? factorRecipe(recipe) : recipe);
	}
	
	public BasicRecipe newRecipe(List<IItemIngredient> itemIngredients, List<IFluidIngredient> fluidIngredients, List<IItemIngredient> itemProducts, List<IFluidIngredient> fluidProducts, List<Object> extras, boolean shapeless) {
		return new BasicRecipe(itemIngredients, fluidIngredients, itemProducts, fluidProducts, extras, shapeless);
	}
	
	public void addGTCERecipes() {
		if (GTCE_INTEGRATION.getBoolean(name)) {
			for (BasicRecipe recipe : recipeList) {
				GTCERecipeHelper.addGTCERecipe(name, recipe);
			}
		}
	}
	
	protected abstract List<Object> fixedExtras(List<Object> extras);
	
	public static class ExtrasFixer {
		
		private final List<Object> extras;
		public final List<Object> fixed = new ArrayList<>();
		
		private final int extrasCount;
		private int currentIndex = 0;
		
		public ExtrasFixer(List<Object> extras) {
			this.extras = extras;
			extrasCount = extras.size();
		}
		
		public <T> void add(Class<? extends T> clazz, T defaultValue) {
			int index = currentIndex++;
			Object extra;
			fixed.add(index < extrasCount && clazz.isInstance(extra = extras.get(index)) ? extra : defaultValue);
		}
	}
	
	protected BasicRecipe factorRecipe(BasicRecipe recipe) {
		if (recipe == null) {
			return null;
		}
		if (!recipe.getItemIngredients().isEmpty() || !recipe.getItemProducts().isEmpty()) {
			return recipe;
		}
		
		IntList stackSizes = new IntArrayList();
		for (IFluidIngredient ingredient : recipe.getFluidIngredients()) {
			stackSizes.addAll(ingredient.getFactors());
		}
		for (IFluidIngredient ingredient : recipe.getFluidProducts()) {
			stackSizes.addAll(ingredient.getFactors());
		}
		stackSizes.addAll(getExtraFactors(recipe.getExtras()));
		
		int hcf = NCMath.hcf(stackSizes.toIntArray());
		if (hcf == 1) {
			return recipe;
		}
		
		List<IFluidIngredient> fluidIngredients = new ArrayList<>(), fluidProducts = new ArrayList<>();
		
		for (IFluidIngredient ingredient : recipe.getFluidIngredients()) {
			fluidIngredients.add(ingredient.getFactoredIngredient(hcf));
		}
		for (IFluidIngredient ingredient : recipe.getFluidProducts()) {
			fluidProducts.add(ingredient.getFactoredIngredient(hcf));
		}
		
		return newRecipe(recipe.getItemIngredients(), fluidIngredients, recipe.getItemProducts(), fluidProducts, getFactoredExtras(recipe.getExtras(), hcf), recipe.isShapeless());
	}
	
	protected IntList getExtraFactors(List<Object> extras) {
		return new IntArrayList();
	}
	
	protected List<Object> getFactoredExtras(List<Object> extras, int factor) {
		return extras;
	}
	
	private static final Object2BooleanMap<String> GTCE_INTEGRATION = new Object2BooleanOpenHashMap<>();
	
	public static void initGTCEIntegration() {
		GTCE_INTEGRATION.put("manufactory", gtce_recipe_integration[0]);
		GTCE_INTEGRATION.put("separator", gtce_recipe_integration[1]);
		GTCE_INTEGRATION.put("decay_hastener", gtce_recipe_integration[2]);
		GTCE_INTEGRATION.put("fuel_reprocessor", gtce_recipe_integration[3]);
		GTCE_INTEGRATION.put("alloy_furnace", gtce_recipe_integration[4]);
		GTCE_INTEGRATION.put("infuser", gtce_recipe_integration[5]);
		GTCE_INTEGRATION.put("melter", gtce_recipe_integration[6]);
		GTCE_INTEGRATION.put("supercooler", gtce_recipe_integration[7]);
		GTCE_INTEGRATION.put("electrolyzer", gtce_recipe_integration[8]);
		GTCE_INTEGRATION.put("assembler", gtce_recipe_integration[9]);
		GTCE_INTEGRATION.put("ingot_former", gtce_recipe_integration[10]);
		GTCE_INTEGRATION.put("pressurizer", gtce_recipe_integration[11]);
		GTCE_INTEGRATION.put("chemical_reactor", gtce_recipe_integration[12]);
		GTCE_INTEGRATION.put("salt_mixer", gtce_recipe_integration[13]);
		GTCE_INTEGRATION.put("crystallizer", gtce_recipe_integration[14]);
		GTCE_INTEGRATION.put("enricher", gtce_recipe_integration[15]);
		GTCE_INTEGRATION.put("extractor", gtce_recipe_integration[16]);
		GTCE_INTEGRATION.put("centrifuge", gtce_recipe_integration[17]);
		GTCE_INTEGRATION.put("rock_crusher", gtce_recipe_integration[18]);
		GTCE_INTEGRATION.put("electric_furnace", gtce_recipe_integration[19]);
	}
	
	@Nullable
	public BasicRecipe buildRecipe(List<?> itemInputs, List<?> fluidInputs, List<?> itemOutputs, List<?> fluidOutputs, List<Object> extras, boolean shapeless) {
		List<IItemIngredient> itemIngredients = new ArrayList<>(), itemProducts = new ArrayList<>();
		List<IFluidIngredient> fluidIngredients = new ArrayList<>(), fluidProducts = new ArrayList<>();
		for (Object obj : itemInputs) {
			if (obj != null && isValidItemInputType(obj)) {
				IItemIngredient input = RecipeHelper.buildItemIngredient(obj);
				if (input == null) {
					return null;
				}
				itemIngredients.add(input);
			}
			else {
				return null;
			}
		}
		for (Object obj : fluidInputs) {
			if (obj != null && isValidFluidInputType(obj)) {
				IFluidIngredient input = RecipeHelper.buildFluidIngredient(obj);
				if (input == null) {
					return null;
				}
				fluidIngredients.add(input);
			}
			else {
				return null;
			}
		}
		for (Object obj : itemOutputs) {
			if (obj != null && isValidItemOutputType(obj)) {
				IItemIngredient output = RecipeHelper.buildItemIngredient(obj);
				if (output == null) {
					return null;
				}
				itemProducts.add(output);
			}
			else {
				return null;
			}
		}
		for (Object obj : fluidOutputs) {
			if (obj != null && isValidFluidOutputType(obj)) {
				IFluidIngredient output = RecipeHelper.buildFluidIngredient(obj);
				if (output == null) {
					return null;
				}
				fluidProducts.add(output);
			}
			else {
				return null;
			}
		}
		if (!isValidRecipe(itemIngredients, fluidIngredients, itemProducts, fluidProducts)) {
			NCUtil.getLogger().info(name + " - a recipe failed to be registered: " + RecipeHelper.getRecipeString(itemIngredients, fluidIngredients, itemProducts, fluidProducts));
		}
		return newRecipe(itemIngredients, fluidIngredients, itemProducts, fluidProducts, fixedExtras(extras), shapeless);
	}
	
	public boolean isValidRecipe(List<IItemIngredient> itemIngredients, List<IFluidIngredient> fluidIngredients, List<IItemIngredient> itemProducts, List<IFluidIngredient> fluidProducts) {
		return itemIngredients.size() == itemInputSize && fluidIngredients.size() == fluidInputSize && itemProducts.size() == itemOutputSize && fluidProducts.size() == fluidOutputSize;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@ZenMethod
	public static BasicRecipeHandler get(String name) {
		return NCRecipes.getHandler(name);
	}
	
	@Override
	@ZenMethod
	public List<BasicRecipe> getRecipeList() {
		return recipeList;
	}
	
	@ZenMethod
	public int getItemInputSize() {
		return itemInputSize;
	}
	
	@ZenMethod
	public int getFluidInputSize() {
		return fluidInputSize;
	}
	
	@ZenMethod
	public int getItemOutputSize() {
		return itemOutputSize;
	}
	
	@ZenMethod
	public int getFluidOutputSize() {
		return fluidOutputSize;
	}
	
	@ZenMethod
	public boolean isShapeless() {
		return isShapeless;
	}
	
	@ZenMethod(value = "addRecipe")
	@Optional.Method(modid = "crafttweaker")
	public void ctAddRecipe(Object... objects) {
		CraftTweakerAPI.apply(new CTAddRecipe(this, Arrays.asList(objects)));
	}
	
	@ZenMethod(value = "removeRecipeWithInput")
	@Optional.Method(modid = "crafttweaker")
	public void ctRemoveRecipeWithInput(crafttweaker.api.item.IIngredient... ctIngredients) {
		CraftTweakerAPI.apply(new CTRemoveRecipe(this, IngredientSorption.INPUT, Arrays.asList(ctIngredients)));
	}
	
	@ZenMethod(value = "removeRecipeWithOutput")
	@Optional.Method(modid = "crafttweaker")
	public void ctRemoveRecipeWithOutput(crafttweaker.api.item.IIngredient... ctIngredients) {
		CraftTweakerAPI.apply(new CTRemoveRecipe(this, IngredientSorption.OUTPUT, Arrays.asList(ctIngredients)));
	}
	
	@ZenMethod(value = "removeAllRecipes")
	@Optional.Method(modid = "crafttweaker")
	public void ctRemoveAllRecipes() {
		CraftTweakerAPI.apply(new CTRemoveAllRecipes(this));
	}
	
	@Override
	public void init() {
		super.init();
		validFluids = RecipeHelper.validFluids(this);
	}
	
	@Override
	protected void fillHashCache() {
		for (BasicRecipe recipe : recipeList) {
			List<Pair<List<ItemStack>, List<FluidStack>>> materialListTuples = new ArrayList<>();
			
			if (!prepareMaterialListTuples(recipe, materialListTuples)) {
				continue;
			}
			
			for (Pair<List<ItemStack>, List<FluidStack>> materials : materialListTuples) {
				if (isShapeless) {
					for (List<ItemStack> items : PermutationHelper.permutations(materials.getLeft())) {
						for (List<FluidStack> fluids : PermutationHelper.permutations(materials.getRight())) {
							addToHashCache(recipe, items, fluids);
						}
					}
				}
				else {
					addToHashCache(recipe, materials.getLeft(), materials.getRight());
				}
			}
		}
	}
	
	protected void addToHashCache(BasicRecipe recipe, List<ItemStack> items, List<FluidStack> fluids) {
		long hash = RecipeHelper.hashMaterials(items, fluids);
		if (recipeCache.containsKey(hash)) {
			recipeCache.get(hash).add(recipe);
		}
		else {
			ObjectSet<BasicRecipe> set = new ObjectOpenHashSet<>();
			set.add(recipe);
			recipeCache.put(hash, set);
		}
	}
	
	protected <T, V extends IIngredient<T>> boolean isValidInput(T stack, int index, Function<BasicRecipe, List<V>> ingredientsFunction) {
		for (BasicRecipe recipe : recipeList) {
			if (isShapeless) {
				for (V input : ingredientsFunction.apply(recipe)) {
					if (input.match(stack, IngredientSorption.NEUTRAL).matches()) {
						return true;
					}
				}
			}
			else {
				if (ingredientsFunction.apply(recipe).get(index).match(stack, IngredientSorption.NEUTRAL).matches()) {
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean isValidItemInput(ItemStack stack, int slot) {
		return isValidInput(stack, slot, BasicRecipe::getItemIngredients);
	}
	
	public boolean isValidFluidInput(FluidStack stack, int tankNumber) {
		return isValidInput(stack, tankNumber, BasicRecipe::getFluidIngredients);
	}
	
	/**
	 * Smart insertion - don't insert if stack is not valid for any possible recipes
	 */
	public <T, U, V extends IIngredient<T>, W extends IIngredient<U>> boolean isValidInput(T stack, int index, List<T> inputs, List<U> associatedInputs, RecipeInfo<BasicRecipe> recipeInfo, int inputSize, int associatedInputSize, Predicate<T> isEmptyFunction, Predicate<U> associatedIsEmptyFunction, Predicate<T> isEqualFunction, Function<BasicRecipe, List<V>> ingredientsFunction, Function<BasicRecipe, List<W>> associatedIngredientsFunction) {
		List<T> otherInputs = inputsExcludingIndex(inputs, index);
		if ((otherInputs.stream().allMatch(isEmptyFunction) && associatedInputs.stream().allMatch(associatedIsEmptyFunction)) || isEqualFunction.test(inputs.get(index))) {
			return isValidInput(stack, index, ingredientsFunction);
		}
		
		if (recipeInfo == null) {
			ObjectSet<BasicRecipe> recipes = new ObjectOpenHashSet<>(recipeList);
			recipeLoop:
			for (BasicRecipe recipe : recipeList) {
				List<V> ingredients = ingredientsFunction.apply(recipe);
				List<W> associatedIngredients = associatedIngredientsFunction.apply(recipe);
				if (isShapeless) {
					stackLoop:
					for (T inputStack : inputs) {
						if (!isEmptyFunction.test(inputStack)) {
							for (V recipeInput : ingredients) {
								if (recipeInput.match(inputStack, IngredientSorption.NEUTRAL).matches()) {
									continue stackLoop;
								}
							}
							recipes.remove(recipe);
							continue recipeLoop;
						}
					}
					
					associatedStackLoop:
					for (U inputStack : associatedInputs) {
						if (!associatedIsEmptyFunction.test(inputStack)) {
							for (W recipeInput : associatedIngredients) {
								if (recipeInput.match(inputStack, IngredientSorption.NEUTRAL).matches()) {
									continue associatedStackLoop;
								}
							}
							recipes.remove(recipe);
							continue recipeLoop;
						}
					}
				}
				else {
					for (int i = 0; i < inputSize; ++i) {
						T inputStack = inputs.get(i);
						if (!isEmptyFunction.test(inputStack) && !ingredients.get(i).match(inputStack, IngredientSorption.NEUTRAL).matches()) {
							recipes.remove(recipe);
							continue recipeLoop;
						}
					}
					
					for (int i = 0; i < associatedInputSize; ++i) {
						U inputStack = associatedInputs.get(i);
						if (!associatedIsEmptyFunction.test(inputStack) && !associatedIngredients.get(i).match(inputStack, IngredientSorption.NEUTRAL).matches()) {
							recipes.remove(recipe);
							continue recipeLoop;
						}
					}
				}
			}
			
			for (BasicRecipe recipe : recipes) {
				if (isValidInputInternal(stack, index, otherInputs, ingredientsFunction.apply(recipe), isEmptyFunction)) {
					return true;
				}
			}
			return false;
		}
		else {
			return isValidInputInternal(stack, index, otherInputs, ingredientsFunction.apply(recipeInfo.recipe), isEmptyFunction);
		}
	}
	
	public boolean isValidItemInput(ItemStack stack, int slot, List<ItemStack> itemInputs, List<Tank> fluidInputs, RecipeInfo<BasicRecipe> recipeInfo) {
		return isValidInput(stack, slot, itemInputs, StreamHelper.map(fluidInputs, Tank::getFluid), recipeInfo, itemInputSize, fluidInputSize, ItemStack::isEmpty, x -> x == null || x.amount <= 0, x -> stack.isItemEqual(x) && StackHelper.areItemStackTagsEqual(stack, x), BasicRecipe::getItemIngredients, BasicRecipe::getFluidIngredients);
	}
	
	public boolean isValidFluidInput(FluidStack stack, int tankNumber, List<Tank> fluidInputs, List<ItemStack> itemInputs, RecipeInfo<BasicRecipe> recipeInfo) {
		return isValidInput(stack, tankNumber, StreamHelper.map(fluidInputs, Tank::getFluid), itemInputs, recipeInfo, fluidInputSize, itemInputSize, x -> x == null || x.amount <= 0, ItemStack::isEmpty, stack == null ? Objects::isNull : x -> (stack.isFluidEqual(x) && FluidStack.areFluidStackTagsEqual(stack, x)), BasicRecipe::getFluidIngredients, BasicRecipe::getItemIngredients);
	}
	
	protected <T, V extends IIngredient<T>> boolean isValidInputInternal(T stack, int index, List<T> otherInputs, List<V> ingredients, Predicate<T> isEmptyFunction) {
		if (isShapeless) {
			for (V input : ingredients) {
				if (input.match(stack, IngredientSorption.NEUTRAL).matches()) {
					for (T other : otherInputs) {
						if (!isEmptyFunction.test(other) && input.match(other, IngredientSorption.NEUTRAL).matches()) {
							return false;
						}
					}
					return true;
				}
			}
			return false;
		}
		else {
			return ingredients.get(index).match(stack, IngredientSorption.NEUTRAL).matches();
		}
	}
	
	protected static <T> List<T> inputsExcludingIndex(List<T> inputs, int index) {
		List<T> inputsExcludingIndex = new ArrayList<>(inputs);
		inputsExcludingIndex.remove(index);
		return inputsExcludingIndex;
	}
}
