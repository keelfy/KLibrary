package keelfy.klibrary.events.entity;

import cpw.mods.fml.common.eventhandler.Cancelable;
import keelfy.klibrary.utils.KBlock;
import net.minecraft.entity.Entity;

/**
 * @author keelfy
 * @date 8 июн. 2019 г.
 */
@Cancelable
public class EntityChangeBlockEvent extends EntityInteractBlockEvent {

	protected KBlock blockTo;

	public EntityChangeBlockEvent(KBlock block, KBlock blockTo, Entity entity) {
		super(block, entity);

		this.blockTo = blockTo;
	}

	public KBlock getTo() {
		return blockTo;
	}
}
