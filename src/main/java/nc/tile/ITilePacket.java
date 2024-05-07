package nc.tile;

import nc.network.NCPacket;
import net.minecraft.entity.player.EntityPlayer;

public interface ITilePacket<PACKET extends NCPacket> extends ITile {
	
	PACKET getTileUpdatePacket();
	
	void onTileUpdatePacket(PACKET message);
	
	default void sendTileUpdatePacketToPlayer(EntityPlayer player) {
		if (!getTileWorld().isRemote) {
			getTileUpdatePacket().sendTo(player);
		}
	}
	
	default void sendTileUpdatePacketToAll() {
		getTileUpdatePacket().sendToAll();
	}
}
