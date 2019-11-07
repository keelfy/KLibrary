package keelfy.klibrary.events.block;

import cpw.mods.fml.common.eventhandler.Cancelable;
import keelfy.klibrary.utils.KBlock;

/**
 * @author keelfy
 */
@Cancelable
public class BlockFromToEvent extends KBlockEvent {

	private final KBlock blockTo;

	public BlockFromToEvent(KBlock blockFrom, KBlock blockTo) {
		super(blockFrom);

		this.blockTo = blockTo;
	}

	public KBlock getBlockTo() {
		return blockTo;
	}
}
