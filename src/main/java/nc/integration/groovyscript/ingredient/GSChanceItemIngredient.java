package nc.integration.groovyscript.ingredient;

import com.cleanroommc.groovyscript.api.GroovyBlacklist;
import com.cleanroommc.groovyscript.api.documentation.annotations.*;
import com.cleanroommc.groovyscript.api.documentation.annotations.MethodDescription.Type;
import com.cleanroommc.groovyscript.helper.Alias;
import com.cleanroommc.groovyscript.registry.VirtualizedRegistry;
import com.google.common.base.CaseFormat;
import nc.integration.groovyscript.*;
import nc.recipe.ingredient.ChanceItemIngredient;

@RegistryDescription
public class GSChanceItemIngredient extends VirtualizedRegistry<Object> {
	
	public GSChanceItemIngredient() {
		super(Alias.generateOf("ChanceItemIngredient", CaseFormat.UPPER_CAMEL));
	}
	
	@GroovyBlacklist
	@Override
	public void onReload() {
	
	}
	
	@MethodDescription(type = Type.QUERY)
	public ChanceItemIngredient create(Object object, int chancePercent) {
		return new ChanceItemIngredient(GSHelper.buildAdditionItemIngredient(object), chancePercent);
	}
	
	@MethodDescription(type = Type.QUERY)
	public ChanceItemIngredient create(Object object, int chancePercent, int minStackSize) {
		return new ChanceItemIngredient(GSHelper.buildAdditionItemIngredient(object), chancePercent, minStackSize);
	}
}
