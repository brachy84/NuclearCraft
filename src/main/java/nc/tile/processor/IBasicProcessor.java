package nc.tile.processor;

import nc.network.tile.processor.ProcessorUpdatePacket;
import nc.tile.processor.info.ProcessorContainerInfoImpl;
import net.minecraft.tileentity.TileEntity;

public interface IBasicProcessor<TILE extends TileEntity & IBasicProcessor<TILE, PACKET>, PACKET extends ProcessorUpdatePacket> extends IProcessor<TILE, PACKET, ProcessorContainerInfoImpl.BasicProcessorContainerInfo<TILE, PACKET>> {

}
