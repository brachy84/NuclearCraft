package nc.tile;

import nc.NuclearCraft;
import nc.network.NCPacket;
import nc.tile.info.TileContainerInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;

import java.util.Set;

public interface ITileGui<TILE extends TileEntity & ITileGui<TILE, PACKET, INFO>, PACKET extends NCPacket, INFO extends TileContainerInfo<TILE>> extends ITilePacket<PACKET> {
	
	INFO getContainerInfo();

	default void openGui(World world, BlockPos pos, EntityPlayer player) {
		FMLNetworkHandler.openGui(player, NuclearCraft.instance, getContainerInfo().getGuiId(), world, pos.getX(), pos.getY(), pos.getZ());
	}

	Set<EntityPlayer> getTileUpdatePacketListeners();

	default void addTileUpdatePacketListener(EntityPlayer player) {
		getTileUpdatePacketListeners().add(player);
		sendTileUpdatePacketToPlayer(player);
	}

	default void removeTileUpdatePacketListener(EntityPlayer player) {
		getTileUpdatePacketListeners().remove(player);
	}

	default void sendTileUpdatePacketToListeners() {
		getTileUpdatePacket().sendTo(getTileUpdatePacketListeners());
	}
}
