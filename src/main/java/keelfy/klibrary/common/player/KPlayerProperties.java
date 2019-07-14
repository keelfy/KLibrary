package keelfy.klibrary.common.player;

import keelfy.klibrary.KLibrary;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldSettings.GameType;
import net.minecraftforge.common.IExtendedEntityProperties;

/**
 * @author keelfy
 */
public final class KPlayerProperties implements IExtendedEntityProperties {

	private static final String NBT_NAME = KLibrary.MOD_ID + "_player";

	private GameType lastTickGameMode;

	public KPlayerProperties() {
		this.lastTickGameMode = GameType.NOT_SET;
	}

	public GameType getLastTickGameMode() {
		return lastTickGameMode;
	}

	public void setLastTickGameMode(GameType lastTickGameMode) {
		this.lastTickGameMode = lastTickGameMode;
	}

	public static void construct(EntityPlayer player) {
		player.registerExtendedProperties(KPlayerProperties.NBT_NAME, new KPlayerProperties());
	}

	public static KPlayerProperties get(EntityPlayer player) {
		KPlayerProperties data = (KPlayerProperties) player.getExtendedProperties(KPlayerProperties.NBT_NAME);
		if (data == null)
			construct(player);
		return data;
	}

	@Override
	public void saveNBTData(NBTTagCompound compound) {}

	@Override
	public void loadNBTData(NBTTagCompound compound) {}

	@Override
	public void init(Entity entity, World world) {}

}
