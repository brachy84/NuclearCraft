package nc.integration.groovyscript;

import com.cleanroommc.groovyscript.api.GroovyBlacklist;
import com.cleanroommc.groovyscript.api.documentation.annotations.*;
import com.cleanroommc.groovyscript.api.documentation.annotations.MethodDescription.Type;
import com.cleanroommc.groovyscript.compat.mods.ModPropertyContainer;
import it.unimi.dsi.fastutil.objects.*;
import nc.recipe.NCRecipes;

public class GSContainer extends ModPropertyContainer {
	
	@GroovyBlacklist
	public static final Object2ObjectMap<String, GSBasicRecipeRegistry> GS_RECIPE_REGISTRY_CACHE = new Object2ObjectOpenHashMap<>();
	
	public GSContainer() {
		super();
		for (String name : NCRecipes.CT_RECIPE_HANDLER_NAME_ARRAY) {
			addRegistry(getRecipeRegistry(name));
		}
		addRegistry(new GSStaticRecipeHandler());
	}
	
	@GroovyBlacklist
	protected static GSBasicRecipeRegistry getRecipeRegistry(String name) {
		GSBasicRecipeRegistry registry = GS_RECIPE_REGISTRY_CACHE.get(name);
		if (registry == null) {
			registry = switch (name) {
				case "fission_moderator", "fission_reflector" -> new GSInfoRecipeRegistry(name);
				default -> new GSStandardRecipeRegistry(name);
			};
			GS_RECIPE_REGISTRY_CACHE.put(name, registry);
		}
		return registry;
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
	
	@GroovyBlacklist
	public static void onReload() {
		for (GSBasicRecipeRegistry registry : GS_RECIPE_REGISTRY_CACHE.values()) {
			registry.onReloadSynchronized();
		}
	}
}
