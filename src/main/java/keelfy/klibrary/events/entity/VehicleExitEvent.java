package keelfy.klibrary.events.entity;

import net.minecraft.entity.Entity;

/**
 * @author keelfy
 */
public class VehicleExitEvent extends KEntityEvent {

	private Entity vehicle;

	public VehicleExitEvent(Entity entity, Entity vehicle) {
		super(entity);

		this.vehicle = vehicle;
	}

	public Entity getVehicle() {
		return vehicle;
	}
}
