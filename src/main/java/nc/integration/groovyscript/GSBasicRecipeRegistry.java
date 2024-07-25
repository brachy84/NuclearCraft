package nc.integration.groovyscript;

import com.cleanroommc.groovyscript.api.GroovyBlacklist;
import com.cleanroommc.groovyscript.api.GroovyLog;
import com.cleanroommc.groovyscript.api.documentation.annotations.MethodDescription;
import com.cleanroommc.groovyscript.api.documentation.annotations.MethodDescription.Type;
import com.cleanroommc.groovyscript.helper.Alias;
import com.cleanroommc.groovyscript.helper.recipe.AbstractRecipeBuilder;
import com.cleanroommc.groovyscript.registry.VirtualizedRegistry;
import com.google.common.base.CaseFormat;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import nc.handler.TileInfoHandler;
import nc.recipe.*;
import nc.recipe.ingredient.*;
import nc.tile.processor.info.ProcessorContainerInfo;
import nc.util.*;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public abstract class GSBasicRecipeRegistry extends VirtualizedRegistry<BasicRecipe> {
	
	@GroovyBlacklist
	protected final Lazy<BasicRecipeHandler> recipeHandler;
	
	public GSBasicRecipeRegistry(String name) {
		super(Alias.generateOf(name, CaseFormat.LOWER_UNDERSCORE));
		this.recipeHandler = new Lazy<>(() -> NCRecipes.getHandler(name));
	}
	
	@GroovyBlacklist
	protected BasicRecipeHandler getRecipeHandler() {
		return recipeHandler.get();
	}
	
	@GroovyBlacklist
	@Override
	public void onReload() {
		BasicRecipeHandler recipeHandler = getRecipeHandler();
		removeScripted().forEach(recipeHandler::removeRecipe);
		restoreFromBackup().forEach(recipeHandler::addRecipe);
		recipeHandler.onReload();
	}
	
	@GroovyBlacklist
	protected void addRecipeInternal(Object... objects) {
		BasicRecipeHandler recipeHandler = getRecipeHandler();
		List<Object> objectList = Arrays.asList(objects);
		BasicRecipe recipe = recipeHandler.buildRecipe(
				StreamHelper.map(objectList.subList(0, recipeHandler.itemInputLastIndex), GSHelper::buildAdditionItemIngredient),
				StreamHelper.map(objectList.subList(recipeHandler.itemInputLastIndex, recipeHandler.fluidInputLastIndex), GSHelper::buildAdditionFluidIngredient),
				StreamHelper.map(objectList.subList(recipeHandler.fluidInputLastIndex, recipeHandler.itemOutputLastIndex), GSHelper::buildAdditionItemIngredient),
				StreamHelper.map(objectList.subList(recipeHandler.itemOutputLastIndex, recipeHandler.fluidOutputLastIndex), GSHelper::buildAdditionFluidIngredient),
				objectList.subList(recipeHandler.fluidOutputLastIndex, objects.length),
				recipeHandler.isShapeless
		);
		addScripted(recipe);
		recipeHandler.addRecipe(recipe);
	}
	
	@GroovyBlacklist
	protected void removeRecipeWithInputInternal(Object... inputs) {
		BasicRecipeHandler recipeHandler = getRecipeHandler();
		List<Object> inputList = Arrays.asList(inputs);
		List<IItemIngredient> itemIngredients = StreamHelper.map(inputList.subList(0, recipeHandler.itemInputLastIndex), GSHelper::buildRemovalItemIngredient);
		List<IFluidIngredient> fluidIngredients = StreamHelper.map(inputList.subList(recipeHandler.itemInputLastIndex, recipeHandler.fluidInputLastIndex), GSHelper::buildRemovalFluidIngredient);
		BasicRecipe recipe = recipeHandler.getRecipeFromIngredients(itemIngredients, fluidIngredients);
		while (recipeHandler.removeRecipe(recipe)) {
			addBackup(recipe);
			recipe = recipeHandler.getRecipeFromIngredients(itemIngredients, fluidIngredients);
		}
	}
	
	@GroovyBlacklist
	protected void removeRecipeWithOutputInternal(Object... outputs) {
		BasicRecipeHandler recipeHandler = getRecipeHandler();
		List<Object> outputList = Arrays.asList(outputs);
		List<IItemIngredient> itemProducts = StreamHelper.map(outputList.subList(0, recipeHandler.itemOutputLastIndex), GSHelper::buildRemovalItemIngredient);
		List<IFluidIngredient> fluidProducts = StreamHelper.map(outputList.subList(recipeHandler.itemOutputLastIndex, recipeHandler.fluidOutputLastIndex), GSHelper::buildRemovalFluidIngredient);
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
	
	@MethodDescription(type = Type.QUERY)
	@Override
	public String getName() {
		return super.getName();
	}
	
	@MethodDescription(type = Type.QUERY)
	public List<BasicRecipe> getRecipeList() {
		return getRecipeHandler().getRecipeList();
	}
	
	@MethodDescription(type = Type.QUERY)
	public int getItemInputSize() {
		return getRecipeHandler().getItemInputSize();
	}
	
	@MethodDescription(type = Type.QUERY)
	public int getFluidInputSize() {
		return getRecipeHandler().getFluidInputSize();
	}
	
	@MethodDescription(type = Type.QUERY)
	public int getItemOutputSize() {
		return getRecipeHandler().getItemOutputSize();
	}
	
	@MethodDescription(type = Type.QUERY)
	public int getFluidOutputSize() {
		return getRecipeHandler().getFluidOutputSize();
	}
	
	@MethodDescription(type = Type.QUERY)
	public boolean isShapeless() {
		return getRecipeHandler().isShapeless();
	}

	public GSGenericRecipeBuilder recipeBuilder() {
		return new GSGenericRecipeBuilder();
	}

	public class GSGenericRecipeBuilder extends AbstractRecipeBuilder<BasicRecipe> {

		private ObjectArrayList<Object> extras = new ObjectArrayList<>(3);

		public GSGenericRecipeBuilder setExtra(int index, Object o) {
            this.extras.ensureCapacity(index + 1);
			if (this.extras.size() <= index) {
                this.extras.size(index + 1);
			}
            this.extras.set(index, o);
            return this;
		}

        // Processors

        @GroovyBlacklist
        private ProcessorContainerInfo<?, ?, ?> getInfo() {
            return TileInfoHandler.getProcessorContainerInfo(getRecipeHandler().name);
        }

		public GSGenericRecipeBuilder durationMultiplier(double val) {
			return setExtra(0, val);
		}

        // convenience method
        public GSGenericRecipeBuilder duration(int val) {
            return durationMultiplier(val / getInfo().defaultProcessTime);
        }

		public GSGenericRecipeBuilder energyMultiplier(double val) {
            return setExtra(1, val);
		}

        // convenience method
        public GSGenericRecipeBuilder energy(int val) {
            return energyMultiplier(val / getInfo().defaultProcessPower);
        }

		public GSGenericRecipeBuilder radiation(double val) {
            return setExtra(2, val);
		}

        // Passive Collector

        public GSGenericRecipeBuilder collectorProductionRate(String val) {
            return setExtra(0, val);
        }

        // Decay Generator

        public GSGenericRecipeBuilder decayGeneratorLifetime(double val) {
            return setExtra(0, val);
        }

        public GSGenericRecipeBuilder decayGeneratorPower(double val) {
            return setExtra(1, val);
        }

        public GSGenericRecipeBuilder decayGeneratorRadiation(double val) {
            return setExtra(2, val);
        }

        // Placement Rule

        public GSGenericRecipeBuilder placementRuleID(String val) {
            return setExtra(0, val);
        }

        // Fission Moderator

        public GSGenericRecipeBuilder fissionModeratorFluxFactor(int val) {
            return setExtra(0, val);
        }

        public GSGenericRecipeBuilder fissionModeratorEfficiency(double val) {
            return setExtra(1, val);
        }

        // Fission Reflector

        public GSGenericRecipeBuilder fissionReflectorEfficiency(double val) {
            return setExtra(0, val);
        }

        public GSGenericRecipeBuilder fissionReflectorReflectivity(double val) {
            return setExtra(1, val);
        }

        // Fission Irradiator

        public GSGenericRecipeBuilder irradiatorFluxRequired(long val) {
            return setExtra(0, val);
        }

        public GSGenericRecipeBuilder irradiatorHeatPerFlux(double val) {
            return setExtra(1, val);
        }

        public GSGenericRecipeBuilder irradiatorProcessEfficiency(double val) {
            return setExtra(2, val);
        }

        public GSGenericRecipeBuilder irradiatorMinFluxPerTick(long val) {
            return setExtra(3, val);
        }

        public GSGenericRecipeBuilder irradiatorMaxFluxPerTick(long val) {
            return setExtra(4, val);
        }

        public GSGenericRecipeBuilder irradiatorBaseProcessRadiation(double val) {
            return setExtra(5, val);
        }

        // Fission

        public GSGenericRecipeBuilder fissionFuelTime(int val) {
            return setExtra(0, val);
        }

        public GSGenericRecipeBuilder saltFissionFuelTime(double val) {
            return setExtra(0, val);
        }

        public GSGenericRecipeBuilder fissionFuelHeat(int val) {
            return setExtra(1, val);
        }

        public GSGenericRecipeBuilder fissionFuelEfficiency(double val) {
            return setExtra(2, val);
        }

        public GSGenericRecipeBuilder fissionFuelCriticality(int val) {
            return setExtra(3, val);
        }

        public GSGenericRecipeBuilder fissionFuelDecayFactor(double val) {
            return setExtra(4, val);
        }

        public GSGenericRecipeBuilder fissionFuelSelfPriming(boolean val) {
            return setExtra(5, val);
        }

        public GSGenericRecipeBuilder fissionFuelRadiation(double val) {
            return setExtra(6, val);
        }

        // Fission Heating

        public GSGenericRecipeBuilder fissionHeatingHeatPerInputMB(int val) {
            return setExtra(0, val);
        }

        // Fission Emergency Cooling

        public GSGenericRecipeBuilder emergencyCoolingHeatPerInputMB(double val) {
            return setExtra(1, val);
        }

        // Fusion

        public GSGenericRecipeBuilder fusionComboTime(double val) {
            return setExtra(0, val);
        }

        public GSGenericRecipeBuilder fusionComboHeat(double val) {
            return setExtra(1, val);
        }

        public GSGenericRecipeBuilder fusionComboOptimalTemperature(double val) {
            return setExtra(2, val);
        }

        public GSGenericRecipeBuilder fusionComboRadiation(double val) {
            return setExtra(3, val);
        }

        // Coolant Heater

        public GSGenericRecipeBuilder coolantHeaterCoolingRate(int val) {
            return setExtra(0, val);
        }

        public GSGenericRecipeBuilder coolantHeaterPlacementRule(String val) {
            return setExtra(1, val);
        }

        // Heat Exchanger

        public GSGenericRecipeBuilder heatExchangerProcessTime(double val) {
            return setExtra(0, val);
        }

        public GSGenericRecipeBuilder heatExchangerInputTemperature(int val) {
            return setExtra(1, val);
        }

        public GSGenericRecipeBuilder heatExchangerOutputTemperature(int val) {
            return setExtra(2, val);
        }

        // Turbine

        public GSGenericRecipeBuilder turbinePowerPerMB(double val) {
            return setExtra(0, val);
        }

        public GSGenericRecipeBuilder turbineExpansionLevel(double val) {
            return setExtra(1, val);
        }

        public GSGenericRecipeBuilder turbineSpinUpMultiplier(double val) {
            return setExtra(2, val);
        }

        public GSGenericRecipeBuilder turbineParticleEffect(String val) {
            return setExtra(3, val);
        }

        public GSGenericRecipeBuilder turbineParticleSpeedMultiplier(double val) {
            return setExtra(4, val);
        }

        // Condenser

        public GSGenericRecipeBuilder condenserProcessTime(double val) {
            return setExtra(0, val);
        }

        public GSGenericRecipeBuilder condenserCondensingTemperature(int val) {
            return setExtra(1, val);
        }

        // Radiation Scrubber

        public GSGenericRecipeBuilder scrubberProcessTime(long val) {
            return setExtra(0, val);
        }

        public GSGenericRecipeBuilder scrubberProcessPower(long val) {
            return setExtra(1, val);
        }

        public GSGenericRecipeBuilder scrubberProcessEfficiency(double val) {
            return setExtra(2, val);
        }

        // Radiation Block Mutations

        public GSGenericRecipeBuilder blockMutationThreshold(double val) {
            return setExtra(0, val);
        }

		@Override
		public String getErrorMsg() {
			return "Error adding Nuclearcraft " + name + " recipe";
		}

		@Override
		public void validate(GroovyLog.Msg msg) {
			validateItems(msg, 0, getItemInputSize(), 0, getItemOutputSize());
			validateFluids(msg, 0, getFluidInputSize(), 0, getFluidOutputSize());
            // validating extras is impossible with this api
		}

		@Override
		public @Nullable BasicRecipe register() {
			if (!validate()) return null;
            BasicRecipeHandler recipeHandler = getRecipeHandler();
            List<?> ii = StreamHelper.map(this.input, GSHelper::buildAdditionItemIngredient);
            List<?> io = StreamHelper.map(this.output, GSHelper::buildAdditionItemIngredient);
            List<?> fi = StreamHelper.map(this.fluidInput, GSHelper::buildAdditionFluidIngredient);
            List<?> fo = StreamHelper.map(this.fluidOutput, GSHelper::buildAdditionFluidIngredient);
            BasicRecipe recipe = recipeHandler.buildRecipe(ii, fi, io, fo, extras, recipeHandler.isShapeless);
            addScripted(recipe);
            recipeHandler.addRecipe(recipe);
            return recipe;
		}
	}
}
