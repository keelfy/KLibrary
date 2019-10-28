package keelfy.klibrary.events.entity;

import cpw.mods.fml.common.eventhandler.Cancelable;
import keelfy.klibrary.utils.KLocation;
import net.minecraft.entity.Entity;

/**
 * @author keelfy
 */
@Cancelable
public class EntityMoveEvent extends KEntityEvent {

	private KLocation from;
	private KLocation to;

	public EntityMoveEvent(Entity entity, KLocation from, KLocation to) {
		super(entity);

		this.from = from;
		this.to = to;
	}

	public KLocation getTo() {
		return to;
	}

	public void setTo(KLocation to) {
		this.to = to;
	}

	public KLocation getFrom() {
		return from;
	}
}
