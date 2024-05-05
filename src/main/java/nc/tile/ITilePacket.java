package nc.tile;

import nc.handler.PacketHandler;
import nc.network.NCPacket;
import net.minecraft.entity.player.*;

public interface ITilePacket<PACKET extends NCPacket> extends ITile {
	
	PACKET getTileUpdatePacket();
	
	void onTileUpdatePacket(PACKET message);
	
	default void sendTileUpdatePacketToPlayer(EntityPlayer player) {
		if (!getTileWorld().isRemote) {
			PacketHandler.instance.sendTo(getTileUpdatePacket(), (EntityPlayerMP) player);
		}
	}
	
	default void sendTileUpdatePacketToAll() {
		PacketHandler.instance.sendToAll(getTileUpdatePacket());
	}
}
