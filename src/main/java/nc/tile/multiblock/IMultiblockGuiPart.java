package nc.tile.multiblock;

import nc.handler.PacketHandler;
import nc.multiblock.*;
import nc.network.multiblock.MultiblockUpdatePacket;
import nc.tile.ITileGui;
import nc.tile.TileContainerInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;

import java.util.Set;

public interface IMultiblockGuiPart<MULTIBLOCK extends Multiblock<MULTIBLOCK, T> & IPacketMultiblock<MULTIBLOCK, T, PACKET>, T extends ITileMultiblockPart<MULTIBLOCK, T>, PACKET extends MultiblockUpdatePacket, GUITILE extends TileEntity & IMultiblockGuiPart<MULTIBLOCK, T, PACKET, GUITILE, INFO>, INFO extends TileContainerInfo<GUITILE>> extends ITileMultiblockPart<MULTIBLOCK, T>, ITileGui<GUITILE, PACKET, INFO> {

	default void sendTileUpdatePacketToPlayer(EntityPlayer player) {
		if (!getTileWorld().isRemote && getMultiblock() != null) {
			PacketHandler.instance.sendTo(getTileUpdatePacket(), (EntityPlayerMP) player);
		}
	}

	default void sendTileUpdatePacketToAll() {
		if (getMultiblock() != null) {
			PacketHandler.instance.sendToAll(getTileUpdatePacket());
		}
	}

	default Set<EntityPlayer> getTileUpdatePacketListeners() {
		MULTIBLOCK multiblock = getMultiblock();
		return multiblock == null ? null : multiblock.getMultiblockUpdatePacketListeners();
	}

	default void addTileUpdatePacketListener(EntityPlayer player) {
		if (getMultiblock() != null) {
			getTileUpdatePacketListeners().add(player);
			sendTileUpdatePacketToPlayer(player);
		}
	}

	default void removeTileUpdatePacketListener(EntityPlayer player) {
		if (getMultiblock() != null) {
			getTileUpdatePacketListeners().remove(player);
		}
	}

	default void sendTileUpdatePacketToListeners() {
		if (getMultiblock() != null) {
			for (EntityPlayer player : getTileUpdatePacketListeners()) {
				PacketHandler.instance.sendTo(getTileUpdatePacket(), (EntityPlayerMP) player);
			}
		}
	}
}
