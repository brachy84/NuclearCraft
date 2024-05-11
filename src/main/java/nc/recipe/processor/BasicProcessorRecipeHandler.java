package nc.recipe.processor;

import nc.recipe.BasicRecipeHandler;

import javax.annotation.Nonnull;
import java.util.List;

public abstract class BasicProcessorRecipeHandler extends BasicRecipeHandler {
	
	public BasicProcessorRecipeHandler(@Nonnull String name, int itemInputSize, int fluidInputSize, int itemOutputSize, int fluidOutputSize) {
		super(name, itemInputSize, fluidInputSize, itemOutputSize, fluidOutputSize);
	}
	
	@Override
	protected List<Object> fixedExtras(List<Object> extras) {
		ExtrasFixer fixer = new ExtrasFixer(extras);
		fixer.add(Double.class, 1D);
		fixer.add(Double.class, 1D);
		fixer.add(Double.class, 0D);
		return fixer.fixed;
	}
}
