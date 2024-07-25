package nc.integration.groovyscript;

import com.cleanroommc.groovyscript.api.GroovyPlugin;
import com.cleanroommc.groovyscript.compat.mods.*;
import nc.Global;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collection;

public class GSPlugin implements GroovyPlugin {
	
	@Override
	public @Nullable GroovyPropertyContainer createGroovyPropertyContainer() {
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
	public @NotNull Collection<String> getAliases() {
		return Arrays.asList("nco", "nuclearcraft_overhauled");
	}

	@Override
	public void onCompatLoaded(GroovyContainer<?> container) {
	
	}
}
