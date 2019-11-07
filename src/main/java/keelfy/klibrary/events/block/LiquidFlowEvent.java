package keelfy.klibrary.events.block;

import cpw.mods.fml.common.eventhandler.Cancelable;
import keelfy.klibrary.utils.KBlock;

/**
 * @author keelfy
 */
@Cancelable
public class LiquidFlowEvent extends KBlockEvent {

	private KBlock blocksTo;

	public LiquidFlowEvent(KBlock liquid, KBlock blocksTo) {
		super(liquid);

		this.blocksTo = blocksTo;
	}

	public KBlock getBlockTo() {
		return blocksTo;
	}
}
