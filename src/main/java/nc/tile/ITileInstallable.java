package nc.tile;

import nc.tile.ITile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;

public interface ITileInstallable extends ITile {

	boolean tryInstall(EntityPlayer player, EnumHand hand, EnumFacing facing);
}
