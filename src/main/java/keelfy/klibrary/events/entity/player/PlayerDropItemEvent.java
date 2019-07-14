package keelfy.klibrary.events.entity.player;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;

/**
 * @author keelfy
 */
public class PlayerDropItemEvent extends KPlayerEvent {

	private EntityItem drop;

	public PlayerDropItemEvent(EntityPlayer player, EntityItem drop) {
		super(player);

		this.drop = drop;
	}

	public EntityItem getDrop() {
		return drop;
	}
}
