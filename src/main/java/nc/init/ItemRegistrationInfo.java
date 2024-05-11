package nc.init;

import net.minecraft.item.Item;

import java.util.function.Consumer;

public class ItemRegistrationInfo<T extends Item> {
	
	public final String modId;
	public final String name;
	public final T item;
	public final Runnable registerItem;
	public final Runnable registerRender;
	
	public ItemRegistrationInfo(String modId, String name, T item, Consumer<ItemRegistrationInfo<T>> registerItem, Consumer<ItemRegistrationInfo<T>> registerRender) {
		this.modId = modId;
		this.name = name;
		this.item = item;
		this.registerItem = () -> registerItem.accept(this);
		this.registerRender = () -> registerRender.accept(this);
	}
}
