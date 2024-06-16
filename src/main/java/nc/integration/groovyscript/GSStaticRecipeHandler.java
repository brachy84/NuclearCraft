package nc.integration.groovyscript;

import com.cleanroommc.groovyscript.api.documentation.annotations.RegistryDescription;
import com.cleanroommc.groovyscript.helper.Alias;
import com.cleanroommc.groovyscript.registry.VirtualizedRegistry;

@RegistryDescription
public class GSStaticRecipeHandler extends VirtualizedRegistry<Object> {
	
	public GSStaticRecipeHandler() {
		super(Alias.generateOf("BasicRecipeHandler"));
	}
	
	@Override
	public void onReload() {
		GSContainer.onReload();
	}
	
	public GSBasicRecipeRegistry get(String name) {
		return GSContainer.getRecipeRegistry(name);
	}
}
