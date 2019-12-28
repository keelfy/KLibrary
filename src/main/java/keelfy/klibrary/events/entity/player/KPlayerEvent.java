package keelfy.klibrary.events.entity.player;

import cpw.mods.fml.common.eventhandler.Event;
import net.minecraft.entity.player.EntityPlayer;

/**
 * @author keelfy
 */
public class KPlayerEvent extends Event {

	protected final EntityPlayer player;

	public KPlayerEvent(final EntityPlayer player) {
		this.player = player;
	}

	public EntityPlayer getPlayer() {
		return player;
	}
}
