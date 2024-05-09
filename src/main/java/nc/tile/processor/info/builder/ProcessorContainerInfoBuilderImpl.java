package nc.tile.processor.info.builder;

import nc.container.ContainerFunction;
import nc.gui.GuiInfoTileFunction;
import nc.network.tile.processor.ProcessorUpdatePacket;
import nc.tile.processor.IBasicProcessor;
import nc.tile.processor.IUpgradableBasicProcessor;
import nc.tile.processor.info.ProcessorContainerInfoImpl;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;

import java.util.function.Supplier;

public class ProcessorContainerInfoBuilderImpl {

    public static final class BasicProcessorContainerInfoBuilder<TILE extends TileEntity & IBasicProcessor<TILE, PACKET>, PACKET extends ProcessorUpdatePacket> extends ProcessorContainerInfoBuilder<TILE, PACKET, ProcessorContainerInfoImpl.BasicProcessorContainerInfo<TILE, PACKET>, BasicProcessorContainerInfoBuilder<TILE, PACKET>> {

        public BasicProcessorContainerInfoBuilder(String modId, String name, Class<TILE> tileClass, Supplier<TILE> tileSupplier, Class<? extends Container> containerClass, ContainerFunction<TILE> containerFunction, Class<? extends GuiContainer> guiClass, GuiInfoTileFunction<TILE> guiFunction) {
            super(modId, name, tileClass, tileSupplier, containerClass, containerFunction, guiClass, guiFunction);
        }

        @Override
        public ProcessorContainerInfoImpl.BasicProcessorContainerInfo<TILE, PACKET> buildContainerInfo() {
            return new ProcessorContainerInfoImpl.BasicProcessorContainerInfo<>(modId, name, tileClass, containerClass, containerFunction, guiClass, guiFunction, configContainerFunction, configGuiFunction, recipeHandlerName, inputTankCapacity, outputTankCapacity, defaultProcessTime, defaultProcessPower, isGenerator, consumesInputs, losesProgress, ocComponentName, guiWH, itemInputGuiXYWH, fluidInputGuiXYWH, itemOutputGuiXYWH, fluidOutputGuiXYWH, playerGuiXY, progressBarGuiXYWHUV, energyBarGuiXYWHUV, machineConfigGuiXY, redstoneControlGuiXY, jeiCategoryEnabled, jeiCategoryUid, jeiTitle, jeiTexture, jeiBackgroundXYWH, jeiTooltipXYWH, jeiClickAreaXYWH);
        }
    }

    public static final class BasicUpgradableProcessorContainerInfoBuilder<TILE extends TileEntity & IUpgradableBasicProcessor<TILE, PACKET>, PACKET extends ProcessorUpdatePacket> extends UpgradableProcessorContainerInfoBuilder<TILE, PACKET, ProcessorContainerInfoImpl.BasicUpgradableProcessorContainerInfo<TILE, PACKET>, BasicUpgradableProcessorContainerInfoBuilder<TILE, PACKET>> {

        public BasicUpgradableProcessorContainerInfoBuilder(String modId, String name, Class<TILE> tileClass, Supplier<TILE> tileSupplier, Class<? extends Container> containerClass, ContainerFunction<TILE> containerFunction, Class<? extends GuiContainer> guiClass, GuiInfoTileFunction<TILE> guiFunction) {
            super(modId, name, tileClass, tileSupplier, containerClass, containerFunction, guiClass, guiFunction);
        }

        @Override
        public ProcessorContainerInfoImpl.BasicUpgradableProcessorContainerInfo<TILE, PACKET> buildContainerInfo() {
            return new ProcessorContainerInfoImpl.BasicUpgradableProcessorContainerInfo<>(modId, name, tileClass, containerClass, containerFunction, guiClass, guiFunction, configContainerFunction, configGuiFunction, recipeHandlerName, inputTankCapacity, outputTankCapacity, defaultProcessTime, defaultProcessPower, isGenerator, consumesInputs, losesProgress, ocComponentName, guiWH, itemInputGuiXYWH, fluidInputGuiXYWH, itemOutputGuiXYWH, fluidOutputGuiXYWH, playerGuiXY, progressBarGuiXYWHUV, energyBarGuiXYWHUV, machineConfigGuiXY, redstoneControlGuiXY, jeiCategoryEnabled, jeiCategoryUid, jeiTitle, jeiTexture, jeiBackgroundXYWH, jeiTooltipXYWH, jeiClickAreaXYWH, speedUpgradeGuiXYWH, energyUpgradeGuiXYWH);
        }
    }
}
