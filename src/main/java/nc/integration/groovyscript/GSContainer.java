package nc.integration.groovyscript;

import com.cleanroommc.groovyscript.api.GroovyBlacklist;
import com.cleanroommc.groovyscript.api.documentation.annotations.*;
import com.cleanroommc.groovyscript.api.documentation.annotations.MethodDescription.Type;
import com.cleanroommc.groovyscript.compat.mods.ModPropertyContainer;
import it.unimi.dsi.fastutil.objects.*;
import nc.integration.groovyscript.ingredient.*;
import nc.recipe.NCRecipes;

public class GSContainer extends ModPropertyContainer {
	
	@GroovyBlacklist
	protected static GSContainer instance;
	
	@GroovyBlacklist
	protected final Object2ObjectMap<String, GSBasicRecipeRegistry> registryCache = new Object2ObjectOpenHashMap<>();
	
	protected GSContainer() {
		super();
		for (String name : NCRecipes.CT_RECIPE_HANDLER_NAME_ARRAY) {
			addRegistry(getRecipeRegistryInternal(name));
		}
		addRegistry(new GSStaticRecipeHandler());
		addRegistry(new GSChanceItemIngredient());
		addRegistry(new GSChanceFluidIngredient());
	}
	
	@GroovyBlacklist
	protected GSBasicRecipeRegistry getRecipeRegistry(String name) {
		GSBasicRecipeRegistry registry = registryCache.get(name);
		if (registry == null) {
			registry = getRecipeRegistryInternal(name);
			addRegistry(registry);
			registryCache.put(name, registry);
		}
		return registry;
	}
	
	@GroovyBlacklist
	protected GSBasicRecipeRegistry getRecipeRegistryInternal(String name) {
		return switch (name) {
			case "fission_moderator", "fission_reflector" -> new GSInfoRecipeRegistry(name);
			default -> new GSStandardRecipeRegistry(name);
		};
	}
	
	@RegistryDescription
	public static class GSInfoRecipeRegistry extends GSBasicRecipeRegistry {
		
		public GSInfoRecipeRegistry(String name) {
			super(name);
		}
		
		@MethodDescription(type = Type.ADDITION)
		public void add(Object... objects) {
			addRecipeInternal(objects);
		}
		
		@MethodDescription(type = Type.REMOVAL)
		public void remove(Object... inputs) {
			removeRecipeWithInputInternal(inputs);
		}
		
		@MethodDescription(type = Type.REMOVAL)
		public void removeAll() {
			removeAllRecipesInternal();
		}
	}
	
	@RegistryDescription
	public static class GSStandardRecipeRegistry extends GSBasicRecipeRegistry {
		
		public GSStandardRecipeRegistry(String name) {
			super(name);
		}
		
		@MethodDescription(type = Type.ADDITION)
		public void addRecipe(Object... objects) {
			addRecipeInternal(objects);
		}
		
		@MethodDescription(type = Type.REMOVAL)
		public void removeRecipeWithInput(Object... inputs) {
			removeRecipeWithInputInternal(inputs);
		}
		
		@MethodDescription(type = Type.REMOVAL)
		public void removeRecipeWithOutput(Object... outputs) {
			removeRecipeWithOutputInternal(outputs);
		}
		
		@MethodDescription(type = Type.REMOVAL)
		public void removeAllRecipes() {
			removeAllRecipesInternal();
		}
	}
}
