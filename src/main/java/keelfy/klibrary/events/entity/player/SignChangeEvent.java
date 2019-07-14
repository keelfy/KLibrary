package keelfy.klibrary.events.entity.player;

import cpw.mods.fml.common.eventhandler.Cancelable;
import keelfy.klibrary.utils.KBlock;
import net.minecraft.entity.player.EntityPlayer;

/**
 * @author keelfy
 * @date 7 июн. 2019 г.
 */
@Cancelable
public class SignChangeEvent extends KPlayerEvent {

	private KBlock locatedBlock;

	public SignChangeEvent(EntityPlayer entity, KBlock block) {
		super(entity);

		this.locatedBlock = block;
	}

	public KBlock getSignBlock() {
		return locatedBlock;
	}
}
