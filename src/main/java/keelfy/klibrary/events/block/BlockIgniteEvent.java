package keelfy.klibrary.events.block;

import cpw.mods.fml.common.eventhandler.Cancelable;
import keelfy.klibrary.utils.KBlock;

/**
 * @author keelfy
 */
@Cancelable
public class BlockIgniteEvent extends KBlockEvent {

	public BlockIgniteEvent(KBlock block) {
		super(block);
	}
}