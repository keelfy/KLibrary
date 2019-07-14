package keelfy.klibrary.events.entity;

import keelfy.klibrary.utils.KBlock;
import net.minecraft.entity.Entity;

/**
 * @author keelfy
 * @date 7 июн. 2019 г.
 */
public class EntityInteractBlockEvent extends KEntityEvent {

	protected KBlock locatedBlock;

	public EntityInteractBlockEvent(KBlock block, Entity entity) {
		super(entity);

		this.locatedBlock = block;
	}

	public KBlock getLocatedBlock() {
		return locatedBlock;
	}
}
