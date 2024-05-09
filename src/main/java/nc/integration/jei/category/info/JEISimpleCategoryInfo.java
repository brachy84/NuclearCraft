package nc.integration.jei.category.info;

import nc.integration.jei.category.JEISimpleRecipeCategory;
import nc.integration.jei.wrapper.JEIRecipeWrapperFunction;
import nc.integration.jei.wrapper.JEISimpleRecipeWrapper;
import nc.recipe.BasicRecipeHandler;
import nc.recipe.NCRecipes;
import nc.tile.info.TileContainerInfoHelper;
import nc.util.CollectionHelper;

import java.util.List;

public class JEISimpleCategoryInfo<WRAPPER extends JEISimpleRecipeWrapper<WRAPPER>> extends JEICategoryInfo<WRAPPER, JEISimpleRecipeCategory<WRAPPER>, JEISimpleCategoryInfo<WRAPPER>> {
	
	public final int itemInputSize;
	public final int fluidInputSize;
	public final int itemOutputSize;
	public final int fluidOutputSize;
	
	public final int[] itemInputSlots;
	public final int[] itemOutputSlots;
	
	public final int[] fluidInputTanks;
	public final int[] fluidOutputTanks;
	
	public final int guiWidth;
	public final int guiHeight;
	
	public final List<int[]> itemInputGuiXYWH;
	public final List<int[]> fluidInputGuiXYWH;
	public final List<int[]> itemOutputGuiXYWH;
	public final List<int[]> fluidOutputGuiXYWH;
	
	public final List<int[]> itemInputStackXY;
	public final List<int[]> itemOutputStackXY;
	
	public final int playerGuiX;
	public final int playerGuiY;
	
	public final int progressBarGuiX;
	public final int progressBarGuiY;
	public final int progressBarGuiW;
	public final int progressBarGuiH;
	public final int progressBarGuiU;
	public final int progressBarGuiV;
	
	public final boolean jeiCategoryEnabled;
	
	public final String jeiCategoryUid;
	public final String jeiTitle;
	public final String jeiTexture;
	
	public final int jeiBackgroundX;
	public final int jeiBackgroundY;
	public final int jeiBackgroundW;
	public final int jeiBackgroundH;
	
	public final int jeiTooltipX;
	public final int jeiTooltipY;
	public final int jeiTooltipW;
	public final int jeiTooltipH;
	
	public JEISimpleCategoryInfo(String modId, String name, Class<WRAPPER> jeiRecipeClass, JEIRecipeWrapperFunction<WRAPPER, JEISimpleRecipeCategory<WRAPPER>, JEISimpleCategoryInfo<WRAPPER>> jeiRecipeFunction, List<Object> jeiCrafters, List<JEIContainerConnection> jeiContainerConnections, int[] guiWH, List<int[]> itemInputGuiXYWH, List<int[]> fluidInputGuiXYWH, List<int[]> itemOutputGuiXYWH, List<int[]> fluidOutputGuiXYWH, int[] playerGuiXY, int[] progressBarGuiXYWHUV, boolean jeiCategoryEnabled, String jeiCategoryUid, String jeiTitle, String jeiTexture, int[] jeiBackgroundXYWH, int[] jeiTooltipXYWH) {
		super(modId, name, JEISimpleRecipeCategory::new, jeiRecipeClass, jeiRecipeFunction, jeiCrafters, jeiContainerConnections);

		itemInputSize = itemInputGuiXYWH.size();
		fluidInputSize = fluidInputGuiXYWH.size();
		itemOutputSize = itemOutputGuiXYWH.size();
		fluidOutputSize = fluidOutputGuiXYWH.size();
		
		itemInputSlots = CollectionHelper.increasingArray(itemInputSize);
		itemOutputSlots = CollectionHelper.increasingArray(itemInputSize, itemOutputSize);
		
		fluidInputTanks = CollectionHelper.increasingArray(fluidInputSize);
		fluidOutputTanks = CollectionHelper.increasingArray(fluidInputSize, fluidOutputSize);
		
		guiWidth = guiWH[0];
		guiHeight = guiWH[1];
		
		this.itemInputGuiXYWH = itemInputGuiXYWH;
		this.fluidInputGuiXYWH = fluidInputGuiXYWH;
		this.itemOutputGuiXYWH = itemOutputGuiXYWH;
		this.fluidOutputGuiXYWH = fluidOutputGuiXYWH;
		
		itemInputStackXY = TileContainerInfoHelper.stackXYList(itemInputGuiXYWH);
		itemOutputStackXY = TileContainerInfoHelper.stackXYList(itemOutputGuiXYWH);
		
		playerGuiX = playerGuiXY[0];
		playerGuiY = playerGuiXY[1];
		
		progressBarGuiX = progressBarGuiXYWHUV[0];
		progressBarGuiY = progressBarGuiXYWHUV[1];
		progressBarGuiW = progressBarGuiXYWHUV[2];
		progressBarGuiH = progressBarGuiXYWHUV[3];
		progressBarGuiU = progressBarGuiXYWHUV[4];
		progressBarGuiV = progressBarGuiXYWHUV[5];
		
		this.jeiCategoryEnabled = jeiCategoryEnabled;
		
		this.jeiCategoryUid = jeiCategoryUid;
		this.jeiTitle = jeiTitle;
		this.jeiTexture = jeiTexture;
		
		jeiBackgroundX = jeiBackgroundXYWH[0];
		jeiBackgroundY = jeiBackgroundXYWH[1];
		jeiBackgroundW = jeiBackgroundXYWH[2];
		jeiBackgroundH = jeiBackgroundXYWH[3];
		
		jeiTooltipX = jeiTooltipXYWH[0];
		jeiTooltipY = jeiTooltipXYWH[1];
		jeiTooltipW = jeiTooltipXYWH[2];
		jeiTooltipH = jeiTooltipXYWH[3];
	}
	
	@Override
	public boolean isJEICategoryEnabled() {
		return jeiCategoryEnabled;
	}
	
	@Override
	public int getItemInputSize() {
		return itemInputSize;
	}
	
	@Override
	public int getFluidInputSize() {
		return fluidInputSize;
	}
	
	@Override
	public int getItemOutputSize() {
		return itemOutputSize;
	}
	
	@Override
	public int getFluidOutputSize() {
		return fluidOutputSize;
	}
	
	@Override
	public int[] getItemInputSlots() {
		return itemInputSlots;
	}
	
	@Override
	public int[] getItemOutputSlots() {
		return itemOutputSlots;
	}
	
	@Override
	public int[] getFluidInputTanks() {
		return fluidInputTanks;
	}
	
	@Override
	public int[] getFluidOutputTanks() {
		return fluidOutputTanks;
	}
	
	@Override
	public List<int[]> getItemInputGuiXYWH() {
		return itemInputGuiXYWH;
	}
	
	@Override
	public List<int[]> getFluidInputGuiXYWH() {
		return fluidInputGuiXYWH;
	}
	
	@Override
	public List<int[]> getItemOutputGuiXYWH() {
		return itemOutputGuiXYWH;
	}
	
	@Override
	public List<int[]> getFluidOutputGuiXYWH() {
		return fluidOutputGuiXYWH;
	}
	
	@Override
	public List<int[]> getItemInputStackXY() {
		return itemInputStackXY;
	}
	
	@Override
	public List<int[]> getItemOutputStackXY() {
		return itemOutputStackXY;
	}

	@Override
	public BasicRecipeHandler getRecipeHandler() {
		return NCRecipes.getHandler(getName());
	}
	
	@Override
	public String getJEICategoryUid() {
		return jeiCategoryUid;
	}
	
	@Override
	public String getJEITitle() {
		return jeiTitle;
	}
	
	@Override
	public String getJEITexture() {
		return jeiTexture;
	}
	
	@Override
	public int getJEIBackgroundX() {
		return jeiBackgroundX;
	}
	
	@Override
	public int getJEIBackgroundY() {
		return jeiBackgroundY;
	}
	
	@Override
	public int getJEIBackgroundW() {
		return jeiBackgroundW;
	}
	
	@Override
	public int getJEIBackgroundH() {
		return jeiBackgroundH;
	}
	
	@Override
	public int getJEITooltipX() {
		return jeiTooltipX;
	}
	
	@Override
	public int getJEITooltipY() {
		return jeiTooltipY;
	}
	
	@Override
	public int getJEITooltipW() {
		return jeiTooltipW;
	}
	
	@Override
	public int getJEITooltipH() {
		return jeiTooltipH;
	}
}
