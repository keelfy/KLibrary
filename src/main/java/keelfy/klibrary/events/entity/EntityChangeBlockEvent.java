package keelfy.klibrary.events.entity;

import cpw.mods.fml.common.eventhandler.Cancelable;
import keelfy.klibrary.utils.KBlock;
import net.minecraft.entity.Entity;

/**
 * @author keelfy
 * @date 8 июн. 2019 г.
 */
@Cancelable
public class EntityChangeBlockEvent extends KEntityEvent {

	protected KBlock block;
	protected KBlock blockTo;

	public EntityChangeBlockEvent(KBlock block, KBlock blockTo, Entity entity) {
		super(entity);

		this.block = block;
		this.blockTo = blockTo;
	}

	public KBlock getOriginalBlock() {
		return block;
	}

	public KBlock getBlockTo() {
		return blockTo;
	}
}
