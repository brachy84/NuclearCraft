package nc.init;

import net.minecraft.block.Block;

import java.util.function.Consumer;

public class BlockRegistrationInfo<T extends Block> {
	
	public final String modId;
	public final String name;
	public final T block;
	public final Runnable registerBlock;
	public final Runnable registerRender;
	
	public BlockRegistrationInfo(String modId, String name, T block, Consumer<BlockRegistrationInfo<T>> registerBlock, Consumer<BlockRegistrationInfo<T>> registerRender) {
		this.modId = modId;
		this.name = name;
		this.block = block;
		this.registerBlock = () -> registerBlock.accept(this);
		this.registerRender = () -> registerRender.accept(this);
	}
}
