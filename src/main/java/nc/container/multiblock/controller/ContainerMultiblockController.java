package nc.container.multiblock.controller;

import nc.container.ContainerInfoTile;
import nc.multiblock.*;
import nc.network.multiblock.MultiblockUpdatePacket;
import nc.tile.TileContainerInfo;
import nc.tile.multiblock.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class ContainerMultiblockController<MULTIBLOCK extends Multiblock<MULTIBLOCK, T> & IPacketMultiblock<MULTIBLOCK, T, PACKET>, T extends ITileMultiblockPart<MULTIBLOCK, T>, PACKET extends MultiblockUpdatePacket, GUITILE extends TileEntity & IMultiblockGuiPart<MULTIBLOCK, T, PACKET, GUITILE, INFO>, INFO extends TileContainerInfo<GUITILE>> extends ContainerInfoTile<GUITILE, PACKET, INFO> {
	
	protected final GUITILE tile;
	
	public ContainerMultiblockController(EntityPlayer player, GUITILE tile) {
		super(tile);
		this.tile = tile;
		
		tile.addTileUpdatePacketListener(player);
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return tile.isUsableByPlayer(player);
	}
	
	@Override
	public void putStackInSlot(int slot, ItemStack stack) {
	
	}
	
	@Override
	public void onContainerClosed(EntityPlayer player) {
		super.onContainerClosed(player);
		tile.removeTileUpdatePacketListener(player);
	}
}
