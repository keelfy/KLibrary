package keelfy.klibrary.events;

import cpw.mods.fml.common.eventhandler.*;
import keelfy.klibrary.utils.KBlock;

/**
 * @author keelfy
 */
public class KBlockEvent extends Event {

	private KBlock locatedBlock;

	public KBlockEvent(KBlock data) {
		this.locatedBlock = data;
	}

	public KBlock getLocatedBlock() {
		return locatedBlock;
	}

	@Cancelable
	public static class BlockIgniteEvent extends KBlockEvent {

		public BlockIgniteEvent(KBlock data) {
			super(data);
		}
	}

	@Cancelable
	public static class BlockBurnEvent extends KBlockEvent {

		public BlockBurnEvent(KBlock data) {
			super(data);
		}
	}
}
