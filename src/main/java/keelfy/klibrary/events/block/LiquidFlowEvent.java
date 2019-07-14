package keelfy.klibrary.events.block;

import cpw.mods.fml.common.eventhandler.*;
import keelfy.klibrary.utils.KBlock;
import net.minecraft.block.Block;

/**
 * @author keelfy
 */
@Cancelable
public class LiquidFlowEvent extends Event {

	private KBlock blockTo;
	private Block liquid;

	public LiquidFlowEvent(KBlock block, Block liquid) {
		this.blockTo = block;
		this.liquid = liquid;
	}

	public KBlock getBlockTo() {
		return blockTo;
	}

	public Block getLiquid() {
		return liquid;
	}
}
