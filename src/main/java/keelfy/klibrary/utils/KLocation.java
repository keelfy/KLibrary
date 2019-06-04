package keelfy.klibrary.utils;

import javax.vecmath.Vector3d;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.*;
import net.minecraft.world.World;

/**
 * @author keelfy
 */
public class KLocation {

	public World world;
	public Vector3d position;

	public float yaw;
	public float pitch;

	public KLocation(EntityPlayer player) {
		this.world = player.getEntityWorld();
		this.position = new Vector3d(player.posX, player.posY, player.posZ);
		this.yaw = player.rotationYaw;
		this.pitch = player.rotationPitch;
	}

	public KLocation(World world, Vector3d position, float yaw, float pitch) {
		this.world = world;
		this.position = position;
		this.yaw = yaw;
		this.pitch = pitch;
	}

	public KLocation(World world, double x, double y, double z, float yaw, float pitch) {
		this(world, new Vector3d(x, y, z), yaw, pitch);
	}

	public KLocation(World world, double x, double y, double z) {
		this(world, x, y, z, 0, 0);
	}

	public void applyToPlayer(EntityPlayerMP player) {
		if (player.getEntityWorld().provider.dimensionId != world.provider.dimensionId)
			player.travelToDimension(world.provider.dimensionId);

		player.playerNetServerHandler.setPlayerLocation(position.x, position.y, position.z, yaw, pitch);
	}

	public void applyToEntity(Entity entity) {
		entity.setLocationAndAngles(position.x, position.y, position.z, yaw, pitch);
	}

	public KBlock getLocatedBlock() {
		return new KBlock(this);
	}

	public Vector3d getPosition() {
		return position;
	}

	public World getWorld() {
		return world;
	}

	public int getBlockX() {
		return (int) this.position.x;
	}

	public int getBlockY() {
		return (int) this.position.y;
	}

	public int getBlockZ() {
		return (int) this.position.z;
	}

	public float getYaw() {
		return this.yaw;
	}

	public float getPitch() {
		return this.pitch;
	}
}
