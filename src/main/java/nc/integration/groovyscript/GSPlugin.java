package nc.integration.groovyscript;

import com.cleanroommc.groovyscript.api.GroovyPlugin;
import com.cleanroommc.groovyscript.compat.mods.*;
import nc.Global;
import org.jetbrains.annotations.NotNull;

public class GSPlugin implements GroovyPlugin {
	
	@Override
	public ModPropertyContainer createModPropertyContainer() {
		return GSContainer.instance = new GSContainer();
	}
	
	@Override
	public @NotNull String getModId() {
		return Global.MOD_ID;
	}
	
	@Override
	public @NotNull String getContainerName() {
		return Global.MOD_NAME;
	}
	
	@Override
	public void onCompatLoaded(GroovyContainer<?> container) {
	
	}
}
