package nc.item;

import nc.enumm.IMetaEnum;
import net.minecraft.util.IStringSerializable;

public interface IItemMeta<T extends Enum<T> & IStringSerializable & IMetaEnum> {
	
	Class<T> getEnumClass();
}
