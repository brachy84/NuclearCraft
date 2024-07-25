package nc.integration.groovyscript;

import com.cleanroommc.groovyscript.api.IIngredient;
import com.cleanroommc.groovyscript.helper.ingredient.*;
import nc.recipe.RecipeHelper;
import nc.recipe.ingredient.*;
import nc.util.StreamHelper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class GSHelper {
	
	public static IItemIngredient buildAdditionItemIngredient(Object object) {
		if (object == null || object.equals(IIngredient.EMPTY)) {
			return new EmptyItemIngredient();
		}
		else if (object instanceof IItemIngredient itemIngredient) {
			return itemIngredient;
		}
		else if (object instanceof ItemStack stack) {
			return RecipeHelper.buildItemIngredient(stack);
		}
		else if (object instanceof OreDictIngredient gsOreStack) {
			return new OreIngredient(gsOreStack.getOreDict(), gsOreStack.getAmount());
		}
		else if (object instanceof OreDictWildcardIngredient gsOreWildcard) {
			return RecipeHelper.buildItemIngredient(StreamHelper.map(gsOreWildcard.getMatchingOreDictionaries(), x -> new OreIngredient(x, gsOreWildcard.getAmount())));
		}
		else if (object instanceof ItemsIngredient gsItemsIngredient) {
			return RecipeHelper.buildItemIngredient(StreamHelper.map(gsItemsIngredient.getMatchingStacks(), GSHelper::buildAdditionItemIngredient));
		}
		else if (object instanceof OrIngredient gsOrIngredient) {
			return RecipeHelper.buildItemIngredient(StreamHelper.map(gsOrIngredient.getMatchingStacks(), GSHelper::buildAdditionItemIngredient));
		}
		else {
			throw invalidIngredientException(object);
		}
	}
	
	public static IFluidIngredient buildAdditionFluidIngredient(Object object) {
		if (object == null || object.equals(IIngredient.EMPTY)) {
			return new EmptyFluidIngredient();
		}
		else if (object instanceof IFluidIngredient fluidIngredient) {
			return fluidIngredient;
		}
		else if (object instanceof FluidStack stack) {
			return RecipeHelper.buildFluidIngredient(stack);
		}
		else {
			throw invalidIngredientException(object);
		}
	}
	
	public static IItemIngredient buildRemovalItemIngredient(Object object) {
		if (object == null || object.equals(IIngredient.EMPTY)) {
			return new EmptyItemIngredient();
		}
		else if (object instanceof IItemIngredient itemIngredient) {
			return itemIngredient;
		}
		else if (object instanceof ItemStack stack) {
			return RecipeHelper.buildItemIngredient(stack);
		}
		else if (object instanceof OreDictIngredient gsOreStack) {
			return new OreIngredient(gsOreStack.getOreDict(), gsOreStack.getAmount());
		}
		else if (object instanceof OreDictWildcardIngredient gsOreWildcard) {
			return RecipeHelper.buildItemIngredient(StreamHelper.map(gsOreWildcard.getMatchingOreDictionaries(), x -> new OreIngredient(x, gsOreWildcard.getAmount())));
		}
		else if (object instanceof ItemsIngredient gsItemsIngredient) {
			return RecipeHelper.buildItemIngredient(StreamHelper.map(gsItemsIngredient.getMatchingStacks(), GSHelper::buildRemovalItemIngredient));
		}
		else if (object instanceof OrIngredient gsOrIngredient) {
			return RecipeHelper.buildItemIngredient(StreamHelper.map(gsOrIngredient.getMatchingStacks(), GSHelper::buildRemovalItemIngredient));
		}
		else {
			throw invalidIngredientException(object);
		}
	}
	
	public static IFluidIngredient buildRemovalFluidIngredient(Object object) {
		if (object == null || object.equals(IIngredient.EMPTY)) {
			return new EmptyFluidIngredient();
		}
		else if (object instanceof IFluidIngredient fluidIngredient) {
			return fluidIngredient;
		}
		else if (object instanceof FluidStack stack) {
			return new FluidIngredient(stack.getFluid().getName(), stack.amount);
		}
		else {
			throw invalidIngredientException(object);
		}
	}
	
	public static RuntimeException invalidIngredientException(Object object) {
		return new IllegalArgumentException(String.format("NuclearCraft: Invalid ingredient: %s, %s", object.getClass().getName(), object));
	}
}
