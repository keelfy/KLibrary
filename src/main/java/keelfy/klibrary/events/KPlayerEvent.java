package keelfy.klibrary.events;

import cpw.mods.fml.common.eventhandler.Event;
import net.minecraft.entity.player.EntityPlayer;

/**
 * @author keelfy
 */
public class KPlayerEvent extends Event {

	private EntityPlayer player;

	public KPlayerEvent(EntityPlayer player) {
		this.player = player;
	}

	public EntityPlayer getPlayer() {
		return player;
	}
}
