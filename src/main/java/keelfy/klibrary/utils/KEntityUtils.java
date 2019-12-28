package keelfy.klibrary.utils;

import java.util.Set;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.WorldServer;

/**
 * @author keelfy
 */
public class KEntityUtils {

	/**
	 * Get all players tracking the given Entity. The Entity must be part of the
	 * World that this Tracker belongs to.
	 * 
	 * @param entity the Entity
	 * @return all players tracking the Entity
	 */
	public static Set<EntityPlayer> getPlayersTrackingEntity(Entity entity) {
		return ((WorldServer) entity.worldObj).getEntityTracker().getTrackingPlayers(entity);
	}

}
