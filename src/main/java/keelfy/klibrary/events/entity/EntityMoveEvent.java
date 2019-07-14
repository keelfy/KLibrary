package keelfy.klibrary.events.entity;

import cpw.mods.fml.common.eventhandler.Cancelable;
import keelfy.klibrary.utils.KVector;
import net.minecraft.entity.Entity;

/**
 * @author keelfy
 */
@Cancelable
public class EntityMoveEvent extends KEntityEvent {

	private KVector from;
	private KVector to;

	public EntityMoveEvent(Entity entity, KVector to) {
		super(entity);

		this.from = new KVector(entity);
		this.to = to;
	}

	public KVector getTo() {
		return to;
	}

	public void setTo(KVector value) {
		this.to = value;
	}

	public KVector getFrom() {
		return from;
	}
}
