package nc.tile.multiblock;

import nc.multiblock.*;
import nc.network.multiblock.MultiblockUpdatePacket;
import nc.tile.TileContainerInfo;
import net.minecraft.tileentity.TileEntity;

public interface IMultiblockController<MULTIBLOCK extends Multiblock<MULTIBLOCK, T> & IPacketMultiblock<MULTIBLOCK, T, PACKET>, T extends ITileMultiblockPart<MULTIBLOCK, T>, PACKET extends MultiblockUpdatePacket, CONTROLLER extends TileEntity & IMultiblockController<MULTIBLOCK, T, PACKET, CONTROLLER, INFO>, INFO extends TileContainerInfo<CONTROLLER>> extends IMultiblockGuiPart<MULTIBLOCK, T, PACKET, CONTROLLER, INFO> {
	
	@Override
	default PACKET getTileUpdatePacket() {
		MULTIBLOCK multiblock = getMultiblock();
		return multiblock == null ? null : multiblock.getMultiblockUpdatePacket();
	}
	
	@Override
	default void onTileUpdatePacket(PACKET message) {
		MULTIBLOCK multiblock = getMultiblock();
		if (multiblock != null) {
			multiblock.onMultiblockUpdatePacket(message);
		}
	}
}
