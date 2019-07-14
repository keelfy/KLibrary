package keelfy.klibrary.events.entity;

import cpw.mods.fml.common.eventhandler.Event;
import net.minecraft.entity.Entity;

/**
 * @author keelfy
 * @date 7 июн. 2019 г.
 */
public class KEntityEvent extends Event {

	protected final Entity entity;

	public KEntityEvent(final Entity entity) {
		this.entity = entity;
	}

	public Entity getEntity() {
		return entity;
	}
}
