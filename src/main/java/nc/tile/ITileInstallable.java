package nc.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.*;

public interface ITileInstallable extends ITile {
	
	boolean tryInstall(EntityPlayer player, EnumHand hand, EnumFacing facing);
}
