package nc.render;

import it.unimi.dsi.fastutil.longs.Long2LongMap;
import it.unimi.dsi.fastutil.longs.Long2LongOpenHashMap;
import nc.network.render.BlockHighlightUpdatePacket;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;

public class BlockHighlightTracker {
	
	private final Long2LongMap highlightMap = new Long2LongOpenHashMap();
	
	public void highlightBlock(long posLong, long highlightTimeMillis) {
		highlightMap.put(posLong, System.currentTimeMillis() + highlightTimeMillis);
	}
	
	public Long2LongMap getHighlightMap() {
		return highlightMap;
	}
	
	public static void sendPacket(EntityPlayerMP player, BlockPos pos, long highlightTimeMillis) {
		new BlockHighlightUpdatePacket(pos, highlightTimeMillis).sendTo(player);
	}
	
	public static void sendPacket(EntityPlayerMP player, long posLong, long highlightTimeMillis) {
		sendPacket(player, BlockPos.fromLong(posLong), highlightTimeMillis);
	}
	
	public static void sendPacketToAll(BlockPos pos, long highlightTimeMillis) {
		new BlockHighlightUpdatePacket(pos, highlightTimeMillis).sendToAll();
	}
	
	public static void sendPacketToAll(long posLong, long highlightTimeMillis) {
		sendPacketToAll(BlockPos.fromLong(posLong), highlightTimeMillis);
	}
}
