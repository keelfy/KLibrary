package keelfy.klibrary.events.entity;

import cpw.mods.fml.common.eventhandler.Cancelable;
import keelfy.klibrary.utils.KBlock;
import net.minecraft.entity.Entity;

/**
 * @author keelfy
 */
@Cancelable
public class EntityInteractBlockEvent extends KEntityEvent {

	protected KBlock locatedBlock;
	protected InteractType type;

	public EntityInteractBlockEvent(KBlock block, Entity entity, InteractType type) {
		super(entity);

		this.locatedBlock = block;
		this.type = type;
	}

	public KBlock getLocatedBlock() {
		return locatedBlock;
	}

	public InteractType getInteractType() {
		return type;
	}

	public static enum InteractType {
		BREAK,
		USE,
		PHYSICAL;
	}
}
