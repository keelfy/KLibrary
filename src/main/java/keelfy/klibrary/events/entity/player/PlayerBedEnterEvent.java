package keelfy.klibrary.events.entity.player;

import cpw.mods.fml.common.eventhandler.Cancelable;
import keelfy.klibrary.utils.KBlock;
import net.minecraft.entity.player.EntityPlayer;

/**
 * @author keelfy
 */
@Cancelable
public class PlayerBedEnterEvent extends KPlayerEvent {

	protected KBlock locatedBlock;

	public PlayerBedEnterEvent(EntityPlayer player, KBlock bed) {
		super(player);

		this.locatedBlock = bed;
	}

	public KBlock getBed() {
		return locatedBlock;
	}
}
