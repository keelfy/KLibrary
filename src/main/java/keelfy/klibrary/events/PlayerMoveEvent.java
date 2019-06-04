package keelfy.klibrary.events;

import cpw.mods.fml.common.eventhandler.Cancelable;
import keelfy.klibrary.utils.KLocation;
import net.minecraft.entity.player.EntityPlayer;

/**
 * @author keelfy
 */
@Cancelable
public class PlayerMoveEvent extends KPlayerEvent {

	private KLocation previous;

	public PlayerMoveEvent(EntityPlayer player, KLocation previous) {
		super(player);

		this.previous = previous;
	}

	public KLocation getPrevious() {
		return previous;
	}
}
