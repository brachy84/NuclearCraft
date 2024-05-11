package nc.block.plant;

import net.minecraft.block.*;
import net.minecraft.block.material.*;

import java.util.Random;

public class NCBlockHugeMushroom extends BlockHugeMushroom {
	
	protected final int maxQuantityDropped;
	
	public NCBlockHugeMushroom(MapColor color, Block smallBlockIn, int maxQuantityDropped) {
		super(Material.WOOD, color, smallBlockIn);
		setHardness(0.2F);
		setSoundType(SoundType.WOOD);
		this.maxQuantityDropped = maxQuantityDropped;
	}
	
	@Override
	public int quantityDropped(Random random) {
		return Math.max(0, random.nextInt(8 + maxQuantityDropped) - 7);
	}
}
