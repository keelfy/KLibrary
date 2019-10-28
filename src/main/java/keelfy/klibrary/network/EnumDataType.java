package keelfy.klibrary.network;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Contains all supported data types to sent in type-marked mode.
 * 
 * @see {@link KNetwork} to find out sending code.
 * 
 * @author keelfy
 */
public enum EnumDataType {

	INTEGER(Integer.class),
	DOUBLE(Double.class),
	FLOAT(Float.class),
	LONG(Long.class),
	SHORT(Short.class),
	BYTE(Byte.class),
	STRING(String.class),
	CHAR(Character.class),
	TAG(NBTTagCompound.class),
	BOOLEAN(Boolean.class),
	ITEMSTACK(ItemStack.class);

	private Class<? extends Object> dataClass;

	private EnumDataType(Class<? extends Object> dataClass) {
		this.dataClass = dataClass;
	}

	public Class<? extends Object> getDataClass() {
		return dataClass;
	}
}
