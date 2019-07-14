package keelfy.klibrary.events.entity;

import net.minecraft.entity.Entity;

/**
 * @author keelfy
 */
public class VehicleEnterEvent extends KEntityEvent {

	private Entity vehicle;

	public VehicleEnterEvent(Entity entity, Entity vehicle) {
		super(entity);

		this.vehicle = vehicle;
	}

	public Entity getVehicle() {
		return vehicle;
	}
}
