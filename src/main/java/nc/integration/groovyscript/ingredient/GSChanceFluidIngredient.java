package nc.integration.groovyscript.ingredient;

import com.cleanroommc.groovyscript.api.GroovyBlacklist;
import com.cleanroommc.groovyscript.api.documentation.annotations.*;
import com.cleanroommc.groovyscript.api.documentation.annotations.MethodDescription.Type;
import com.cleanroommc.groovyscript.helper.Alias;
import com.cleanroommc.groovyscript.registry.VirtualizedRegistry;
import com.google.common.base.CaseFormat;
import nc.integration.groovyscript.GSHelper;
import nc.recipe.ingredient.ChanceFluidIngredient;

@RegistryDescription
public class GSChanceFluidIngredient extends VirtualizedRegistry<Object> {
	
	public GSChanceFluidIngredient() {
		super(Alias.generateOf("ChanceFluidIngredient", CaseFormat.UPPER_CAMEL));
	}
	
	@GroovyBlacklist
	@Override
	public void onReload() {
	
	}
	
	@MethodDescription(type = Type.QUERY)
	public ChanceFluidIngredient create(Object object, int chancePercent, int stackDiff) {
		return new ChanceFluidIngredient(GSHelper.buildAdditionFluidIngredient(object), chancePercent, stackDiff);
	}
	
	@MethodDescription(type = Type.QUERY)
	public ChanceFluidIngredient create(Object object, int chancePercent, int stackDiff, int minStackSize) {
		return new ChanceFluidIngredient(GSHelper.buildAdditionFluidIngredient(object), chancePercent, stackDiff, minStackSize);
	}
}