package keelfy.klibrary.events.block;

import cpw.mods.fml.common.eventhandler.*;
import keelfy.klibrary.utils.KBlock;

/**
 * @author keelfy
 */
public class KBlockEvent extends Event {

	private KBlock locatedBlock;

	public KBlockEvent(KBlock block) {
		this.locatedBlock = block;
	}

	public KBlock getLocatedBlock() {
		return locatedBlock;
	}

	@Cancelable
	public static class BlockBurnEvent extends KBlockEvent {

		public BlockBurnEvent(KBlock data) {
			super(data);
		}
	}
}
