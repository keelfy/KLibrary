package keelfy.klibrary.events.entity.player;

import cpw.mods.fml.common.eventhandler.Cancelable;
import keelfy.klibrary.utils.KLocation;
import net.minecraft.entity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/**
 * @author keelfy
 */
@Cancelable
public class SpawnEggEvent extends KPlayerEvent {

	private KLocation location;
	private Class<? extends Entity> entity;
	private ItemStack spawnEgg;

	public SpawnEggEvent(EntityPlayer player, KLocation location, Class<? extends Entity> entity, ItemStack spawnEgg) {
		super(player);

		this.location = location;
		this.entity = entity;
		this.spawnEgg = spawnEgg;
	}

	public SpawnEggEvent(EntityPlayer player, KLocation location, ItemStack spawnEgg) {
		super(player);

		this.location = location;
		this.spawnEgg = spawnEgg;

		EntityList.EntityEggInfo entityegginfo = (EntityList.EntityEggInfo) EntityList.entityEggs.get(Integer.valueOf(spawnEgg.getItemDamage()));
		entity = (Class<? extends Entity>) EntityList.IDtoClassMapping.get(entityegginfo.spawnedID);
	}

	public Class<? extends Entity> getEntityClass() {
		return entity;
	}

	public KLocation getLocation() {
		return location;
	}

	public ItemStack getSpawnEgg() {
		return spawnEgg;
	}
}
