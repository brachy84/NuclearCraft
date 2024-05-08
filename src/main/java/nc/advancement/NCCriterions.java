package nc.advancement;

import com.google.common.collect.Lists;

import java.util.List;

public class NCCriterions {
	
	public static final NCCriterionTrigger SOLID_FISSION_ASSEMBLED = new NCCriterionTrigger("solid_fission_reactor_assembled");
	public static final NCCriterionTrigger SALT_FISSION_ASSEMBLED = new NCCriterionTrigger("salt_fission_reactor_assembled");
	public static final NCCriterionTrigger HEAT_EXCHANGER_ASSEMBLED = new NCCriterionTrigger("heat_exchanger_assembled");
	public static final NCCriterionTrigger TURBINE_ASSEMBLED = new NCCriterionTrigger("turbine_assembled");
	public static final NCCriterionTrigger CONDENSER_ASSEMBLED = new NCCriterionTrigger("condenser_assembled");
	
	public static final List<NCCriterionTrigger> CRITERION_TRIGGERS = Lists.newArrayList(SOLID_FISSION_ASSEMBLED, SALT_FISSION_ASSEMBLED, HEAT_EXCHANGER_ASSEMBLED, TURBINE_ASSEMBLED, CONDENSER_ASSEMBLED);
}
