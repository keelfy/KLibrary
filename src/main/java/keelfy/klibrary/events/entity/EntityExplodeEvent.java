package keelfy.klibrary.events.entity;

import java.util.List;

import keelfy.klibrary.utils.*;
import net.minecraft.entity.Entity;

/**
 * Called when entity explodes.
 * 
 * @author keelfy
 */
public class EntityExplodeEvent extends KEntityEvent {

	protected List<KBlock> damagedBlocks;
	protected KLocation explosionLocation;

	public EntityExplodeEvent(Entity entity, List<KBlock> damagedBlocks, KLocation location) {
		super(entity);

		this.damagedBlocks = damagedBlocks;
	}

	public List<KBlock> getDamagedBlocks() {
		return damagedBlocks;
	}

	public KLocation getLocation() {
		return explosionLocation;
	}
}
