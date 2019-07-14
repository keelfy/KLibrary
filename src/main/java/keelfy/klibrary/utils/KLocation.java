package keelfy.klibrary.utils;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;

/**
 * @author keelfy
 */
public class KLocation {

	protected World world;
	protected KVector position;

	protected float yaw;
	protected float pitch;

	public KLocation(World world, KVector position, float yaw, float pitch) {
		this.world = world;
		this.position = position;
		this.yaw = yaw;
		this.pitch = pitch;
	}

	public KLocation(Entity entity) {
		this(entity.worldObj, new KVector(entity), entity.rotationYaw, entity.rotationPitch);
	}

	public KLocation(World world, KVector position) {
		this(world, position, 0, 0);
	}

	public KLocation(World world, double x, double y, double z, float yaw, float pitch) {
		this(world, new KVector(x, y, z), yaw, pitch);
	}

	public KLocation(World world, double x, double y, double z) {
		this(world, x, y, z, 0, 0);
	}

	public KLocation applyToPlayer(EntityPlayerMP player) {
		if (player.getEntityWorld().provider.dimensionId != world.provider.dimensionId)
			player.travelToDimension(world.provider.dimensionId);

		player.playerNetServerHandler.setPlayerLocation(position.x, position.y, position.z, yaw, pitch);
		return this;
	}

	public KLocation applyToEntity(Entity entity) {
		entity.setLocationAndAngles(position.x, position.y, position.z, yaw, pitch);
		return this;
	}

	public KBlock getLocatedBlock() {
		return new KBlock(this);
	}

	public KLocation setPosition(KVector vector) {
		this.position = vector;
		return this;
	}

	public KLocation setPosition(double x, double y, double z) {
		this.position = new KVector(x, y, z);
		return this;
	}

	public KLocation setAngles(float yaw, float pitch) {
		this.yaw = yaw;
		this.pitch = pitch;
		return this;
	}

	public KVector getPosition() {
		return position;
	}

	public KLocation add(double x, double y, double z) {
		return this.setPosition(getPosition().add(x, y, z));
	}

	public World getWorld() {
		return world;
	}

	public KLocation setYaw(float value) {
		this.yaw = value;
		return this;
	}

	public KLocation setPitch(float value) {
		this.pitch = value;
		return this;
	}

	public float getYaw() {
		return this.yaw;
	}

	public float getPitch() {
		return this.pitch;
	}

	/**
	 * Tests whether two different locations are in two different blocks.
	 *
	 * @param a The first location
	 * @param b The second location
	 * @return Whether the two locations are two different blocks
	 */
	public static boolean isDifferentBlock(KLocation a, KLocation b) {
		return a.getPosition().getBlockX() != b.getPosition().getBlockX() || a.getPosition().getBlockY() != b.getPosition().getBlockY() || a.getPosition().getBlockZ() != b.getPosition().getBlockZ();
	}
}
