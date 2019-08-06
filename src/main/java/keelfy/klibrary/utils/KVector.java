package keelfy.klibrary.utils;

import java.awt.geom.AffineTransform;

import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

/**
 * An immutable 3-dimensional vector.
 * 
 * @author sk89q
 */
public class KVector implements Comparable<KVector> {

	public static final KVector ZERO = new KVector(0, 0, 0);
	public static final KVector UNIT_X = new KVector(1, 0, 0);
	public static final KVector UNIT_Y = new KVector(0, 1, 0);
	public static final KVector UNIT_Z = new KVector(0, 0, 1);
	public static final KVector ONE = new KVector(1, 1, 1);

	protected final double x, y, z;

	/**
	 * Construct an instance.
	 *
	 * @param x the X coordinate
	 * @param y the Y coordinate
	 * @param z the Z coordinate
	 */
	public KVector(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Construct an instance.
	 *
	 * @param x the X coordinate
	 * @param y the Y coordinate
	 * @param z the Z coordinate
	 */
	public KVector(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Construct an instance.
	 *
	 * @param x the X coordinate
	 * @param y the Y coordinate
	 * @param z the Z coordinate
	 */
	public KVector(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Copy another vector.
	 *
	 * @param other another vector to make a copy of
	 */
	public KVector(KVector other) {
		this.x = other.x;
		this.y = other.y;
		this.z = other.z;
	}

	/**
	 * Create vector from entity's position.
	 * 
	 * @param entity from
	 */
	public KVector(Entity entity) {
		this.x = entity.posX;
		this.y = entity.posY;
		this.z = entity.posZ;
	}

	/**
	 * Construct a new instance with X, Y, and Z coordinates set to 0.
	 *
	 * <p>
	 * One can also refer to a static {@link #ZERO}.
	 * </p>
	 */
	public KVector() {
		this.x = 0;
		this.y = 0;
		this.z = 0;
	}

	/**
	 * Get the X coordinate.
	 *
	 * @return the x coordinate
	 */
	public double getX() {
		return x;
	}

	/**
	 * Get the X coordinate rounded.
	 *
	 * @return the x coordinate
	 */
	public int getBlockX() {
		return MathHelper.floor_double(x);
	}

	/**
	 * Set the X coordinate.
	 *
	 * @param x the new X
	 * @return a new vector
	 */
	public KVector setX(double x) {
		return new KVector(x, y, z);
	}

	/**
	 * Set the X coordinate.
	 *
	 * @param x the X coordinate
	 * @return new vector
	 */
	public KVector setX(int x) {
		return new KVector(x, y, z);
	}

	/**
	 * Get the Y coordinate.
	 *
	 * @return the y coordinate
	 */
	public double getY() {
		return y;
	}

	/**
	 * Get the Y coordinate rounded.
	 *
	 * @return the y coordinate
	 */
	public int getBlockY() {
		return MathHelper.floor_double(y);
	}

	/**
	 * Set the Y coordinate.
	 *
	 * @param y the new Y
	 * @return a new vector
	 */
	public KVector setY(double y) {
		return new KVector(x, y, z);
	}

	/**
	 * Set the Y coordinate.
	 *
	 * @param y the new Y
	 * @return a new vector
	 */
	public KVector setY(int y) {
		return new KVector(x, y, z);
	}

	/**
	 * Get the Z coordinate.
	 *
	 * @return the z coordinate
	 */
	public double getZ() {
		return z;
	}

	/**
	 * Get the Z coordinate rounded.
	 *
	 * @return the z coordinate
	 */
	public int getBlockZ() {
		return MathHelper.floor_double(z);
	}

	/**
	 * Set the Z coordinate.
	 *
	 * @param z the new Z
	 * @return a new vector
	 */
	public KVector setZ(double z) {
		return new KVector(x, y, z);
	}

	/**
	 * Set the Z coordinate.
	 *
	 * @param z the new Z
	 * @return a new vector
	 */
	public KVector setZ(int z) {
		return new KVector(x, y, z);
	}

	/**
	 * Add another vector to this vector and return the result as a new vector.
	 *
	 * @param other the other vector
	 * @return a new vector
	 */
	public KVector add(KVector other) {
		return new KVector(x + other.x, y + other.y, z + other.z);
	}

	/**
	 * Add another vector to this vector and return the result as a new vector.
	 *
	 * @param x the value to add
	 * @param y the value to add
	 * @param z the value to add
	 * @return a new vector
	 */
	public KVector add(double x, double y, double z) {
		return new KVector(this.x + x, this.y + y, this.z + z);
	}

	/**
	 * Add another vector to this vector and return the result as a new vector.
	 *
	 * @param x the value to add
	 * @param y the value to add
	 * @param z the value to add
	 * @return a new vector
	 */
	public KVector add(int x, int y, int z) {
		return new KVector(this.x + x, this.y + y, this.z + z);
	}

	/**
	 * Add a list of vectors to this vector and return the result as a new vector.
	 *
	 * @param others an array of vectors
	 * @return a new vector
	 */
	public KVector add(KVector... others) {
		double newX = x, newY = y, newZ = z;

		for (KVector other : others) {
			newX += other.x;
			newY += other.y;
			newZ += other.z;
		}

		return new KVector(newX, newY, newZ);
	}

	/**
	 * Subtract another vector from this vector and return the result as a new
	 * vector.
	 *
	 * @param other the other vector
	 * @return a new vector
	 */
	public KVector subtract(KVector other) {
		return new KVector(x - other.x, y - other.y, z - other.z);
	}

	/**
	 * Subtract another vector from this vector and return the result as a new
	 * vector.
	 *
	 * @param x the value to subtract
	 * @param y the value to subtract
	 * @param z the value to subtract
	 * @return a new vector
	 */
	public KVector subtract(double x, double y, double z) {
		return new KVector(this.x - x, this.y - y, this.z - z);
	}

	/**
	 * Subtract another vector from this vector and return the result as a new
	 * vector.
	 *
	 * @param x the value to subtract
	 * @param y the value to subtract
	 * @param z the value to subtract
	 * @return a new vector
	 */
	public KVector subtract(int x, int y, int z) {
		return new KVector(this.x - x, this.y - y, this.z - z);
	}

	/**
	 * Subtract a list of vectors from this vector and return the result as a new
	 * vector.
	 *
	 * @param others an array of vectors
	 * @return a new vector
	 */
	public KVector subtract(KVector... others) {
		double newX = x, newY = y, newZ = z;

		for (KVector other : others) {
			newX -= other.x;
			newY -= other.y;
			newZ -= other.z;
		}

		return new KVector(newX, newY, newZ);
	}

	/**
	 * Multiply this vector by another vector on each component.
	 *
	 * @param other the other vector
	 * @return a new vector
	 */
	public KVector multiply(KVector other) {
		return new KVector(x * other.x, y * other.y, z * other.z);
	}

	/**
	 * Multiply this vector by another vector on each component.
	 *
	 * @param x the value to multiply
	 * @param y the value to multiply
	 * @param z the value to multiply
	 * @return a new vector
	 */
	public KVector multiply(double x, double y, double z) {
		return new KVector(this.x * x, this.y * y, this.z * z);
	}

	/**
	 * Multiply this vector by another vector on each component.
	 *
	 * @param x the value to multiply
	 * @param y the value to multiply
	 * @param z the value to multiply
	 * @return a new vector
	 */
	public KVector multiply(int x, int y, int z) {
		return new KVector(this.x * x, this.y * y, this.z * z);
	}

	/**
	 * Multiply this vector by zero or more vectors on each component.
	 *
	 * @param others an array of vectors
	 * @return a new vector
	 */
	public KVector multiply(KVector... others) {
		double newX = x, newY = y, newZ = z;

		for (KVector other : others) {
			newX *= other.x;
			newY *= other.y;
			newZ *= other.z;
		}

		return new KVector(newX, newY, newZ);
	}

	/**
	 * Perform scalar multiplication and return a new vector.
	 *
	 * @param n the value to multiply
	 * @return a new vector
	 */
	public KVector multiply(double n) {
		return new KVector(this.x * n, this.y * n, this.z * n);
	}

	/**
	 * Perform scalar multiplication and return a new vector.
	 *
	 * @param n the value to multiply
	 * @return a new vector
	 */
	public KVector multiply(float n) {
		return new KVector(this.x * n, this.y * n, this.z * n);
	}

	/**
	 * Perform scalar multiplication and return a new vector.
	 *
	 * @param n the value to multiply
	 * @return a new vector
	 */
	public KVector multiply(int n) {
		return new KVector(this.x * n, this.y * n, this.z * n);
	}

	/**
	 * Divide this vector by another vector on each component.
	 *
	 * @param other the other vector
	 * @return a new vector
	 */
	public KVector divide(KVector other) {
		return new KVector(x / other.x, y / other.y, z / other.z);
	}

	/**
	 * Divide this vector by another vector on each component.
	 *
	 * @param x the value to divide by
	 * @param y the value to divide by
	 * @param z the value to divide by
	 * @return a new vector
	 */
	public KVector divide(double x, double y, double z) {
		return new KVector(this.x / x, this.y / y, this.z / z);
	}

	/**
	 * Divide this vector by another vector on each component.
	 *
	 * @param x the value to divide by
	 * @param y the value to divide by
	 * @param z the value to divide by
	 * @return a new vector
	 */
	public KVector divide(int x, int y, int z) {
		return new KVector(this.x / x, this.y / y, this.z / z);
	}

	/**
	 * Perform scalar division and return a new vector.
	 *
	 * @param n the value to divide by
	 * @return a new vector
	 */
	public KVector divide(int n) {
		return new KVector(x / n, y / n, z / n);
	}

	/**
	 * Perform scalar division and return a new vector.
	 *
	 * @param n the value to divide by
	 * @return a new vector
	 */
	public KVector divide(double n) {
		return new KVector(x / n, y / n, z / n);
	}

	/**
	 * Perform scalar division and return a new vector.
	 *
	 * @param n the value to divide by
	 * @return a new vector
	 */
	public KVector divide(float n) {
		return new KVector(x / n, y / n, z / n);
	}

	/**
	 * Get the length of the vector.
	 *
	 * @return length
	 */
	public double length() {
		return Math.sqrt(x * x + y * y + z * z);
	}

	/**
	 * Get the length, squared, of the vector.
	 *
	 * @return length, squared
	 */
	public double lengthSq() {
		return x * x + y * y + z * z;
	}

	/**
	 * Get the distance between this vector and another vector.
	 *
	 * @param other the other vector
	 * @return distance
	 */
	public double distance(KVector other) {
		return Math.sqrt(Math.pow(other.x - x, 2) + Math.pow(other.y - y, 2) + Math.pow(other.z - z, 2));
	}

	/**
	 * Get the distance between this vector and another vector, squared.
	 *
	 * @param other the other vector
	 * @return distance
	 */
	public double distanceSq(KVector other) {
		return Math.pow(other.x - x, 2) + Math.pow(other.y - y, 2) + Math.pow(other.z - z, 2);
	}

	/**
	 * Get the normalized vector, which is the vector divided by its length, as a
	 * new vector.
	 *
	 * @return a new vector
	 */
	public KVector normalize() {
		return divide(length());
	}

	/**
	 * Gets the dot product of this and another vector.
	 *
	 * @param other the other vector
	 * @return the dot product of this and the other vector
	 */
	public double dot(KVector other) {
		return x * other.x + y * other.y + z * other.z;
	}

	/**
	 * Gets the cross product of this and another vector.
	 *
	 * @param other the other vector
	 * @return the cross product of this and the other vector
	 */
	public KVector cross(KVector other) {
		return new KVector(y * other.z - z * other.y, z * other.x - x * other.z, x * other.y - y * other.x);
	}

	/**
	 * Checks to see if a vector is contained with another.
	 *
	 * @param min the minimum point (X, Y, and Z are the lowest)
	 * @param max the maximum point (X, Y, and Z are the lowest)
	 * @return true if the vector is contained
	 */
	public boolean containedWithin(KVector min, KVector max) {
		return x >= min.x && x <= max.x && y >= min.y && y <= max.y && z >= min.z && z <= max.z;
	}

	/**
	 * Checks to see if a vector is contained with another, comparing using discrete
	 * comparisons, inclusively.
	 *
	 * @param min the minimum point (X, Y, and Z are the lowest)
	 * @param max the maximum point (X, Y, and Z are the lowest)
	 * @return true if the vector is contained
	 */
	public boolean containedWithinBlock(KVector min, KVector max) {
		return getBlockX() >= min.getBlockX() && getBlockX() <= max.getBlockX() && getBlockY() >= min.getBlockY() && getBlockY() <= max.getBlockY() && getBlockZ() >= min.getBlockZ() && getBlockZ() <= max.getBlockZ();
	}

	/**
	 * Clamp the Y component.
	 *
	 * @param min the minimum value
	 * @param max the maximum value
	 * @return a new vector
	 */
	public KVector clampY(int min, int max) {
		return new KVector(x, Math.max(min, Math.min(max, y)), z);
	}

	/**
	 * Floors the values of all components.
	 *
	 * @return a new vector
	 */
	public KVector floor() {
		return new KVector(Math.floor(x), Math.floor(y), Math.floor(z));
	}

	/**
	 * Rounds all components up.
	 *
	 * @return a new vector
	 */
	public KVector ceil() {
		return new KVector(Math.ceil(x), Math.ceil(y), Math.ceil(z));
	}

	/**
	 * Rounds all components to the closest integer.
	 *
	 * <p>
	 * Components &lt; 0.5 are rounded down, otherwise up.
	 * </p>
	 *
	 * @return a new vector
	 */
	public KVector round() {
		return new KVector(Math.floor(x + 0.5), Math.floor(y + 0.5), Math.floor(z + 0.5));
	}

	/**
	 * Returns a vector with the absolute values of the components of this vector.
	 *
	 * @return a new vector
	 */
	public KVector positive() {
		return new KVector(Math.abs(x), Math.abs(y), Math.abs(z));
	}

	/**
	 * Perform a 2D transformation on this vector and return a new one.
	 *
	 * @param angle      in degrees
	 * @param aboutX     about which x coordinate to rotate
	 * @param aboutZ     about which z coordinate to rotate
	 * @param translateX what to add after rotation
	 * @param translateZ what to add after rotation
	 * @return a new vector
	 * @see AffineTransform another method to transform vectors
	 */
	public KVector transform2D(double angle, double aboutX, double aboutZ, double translateX, double translateZ) {
		angle = Math.toRadians(angle);
		double x = this.x - aboutX;
		double z = this.z - aboutZ;
		double x2 = x * Math.cos(angle) - z * Math.sin(angle);
		double z2 = x * Math.sin(angle) + z * Math.cos(angle);

		return new KVector(x2 + aboutX + translateX, y, z2 + aboutZ + translateZ);
	}

	/**
	 * Returns whether this vector is collinear with another vector.
	 *
	 * @param other the other vector
	 * @return true if collinear
	 */
	public boolean isCollinearWith(KVector other) {
		if (x == 0 && y == 0 && z == 0) {
			// this is a zero vector
			return true;
		}

		final double otherX = other.x;
		final double otherY = other.y;
		final double otherZ = other.z;

		if (otherX == 0 && otherY == 0 && otherZ == 0) {
			// other is a zero vector
			return true;
		}

		if ((x == 0) != (otherX == 0))
			return false;
		if ((y == 0) != (otherY == 0))
			return false;
		if ((z == 0) != (otherZ == 0))
			return false;

		final double quotientX = otherX / x;
		if (!Double.isNaN(quotientX)) {
			return other.equals(multiply(quotientX));
		}

		final double quotientY = otherY / y;
		if (!Double.isNaN(quotientY)) {
			return other.equals(multiply(quotientY));
		}

		final double quotientZ = otherZ / z;
		if (!Double.isNaN(quotientZ)) {
			return other.equals(multiply(quotientZ));
		}

		throw new RuntimeException("This should not happen");
	}

	/**
	 * Get this vector's pitch as used within the game.
	 *
	 * @return pitch in radians
	 */
	public float toPitch() {
		double x = getX();
		double z = getZ();

		if (x == 0 && z == 0) {
			return getY() > 0 ? -90 : 90;
		} else {
			double x2 = x * x;
			double z2 = z * z;
			double xz = Math.sqrt(x2 + z2);
			return (float) Math.toDegrees(Math.atan(-getY() / xz));
		}
	}

	/**
	 * Get this vector's yaw as used within the game.
	 *
	 * @return yaw in radians
	 */
	public float toYaw() {
		double x = getX();
		double z = getZ();

		double t = Math.atan2(-x, z);
		double _2pi = 2 * Math.PI;

		return (float) Math.toDegrees(((t + _2pi) % _2pi));
	}

	/**
	 * Create a new {@code KBlockVector} using the given components.
	 *
	 * @param x the X coordinate
	 * @param y the Y coordinate
	 * @param z the Z coordinate
	 * @return a new {@code BlockVector}
	 */
	public static KBlockVector toBlockPoint(double x, double y, double z) {
		return new KBlockVector(Math.floor(x), Math.floor(y), Math.floor(z));
	}

	/**
	 * Create a new {@code KBlockVector} from this vector.
	 *
	 * @return a new {@code KBlockVector}
	 */
	public KBlockVector toBlockPoint() {
		return new KBlockVector(Math.floor(x), Math.floor(y), Math.floor(z));
	}

	/**
	 * Create a new {@code KBlockVector} from this vector.
	 *
	 * @return a new {@code KBlockVector}
	 */
	public KBlockVector toBlockVector() {
		return new KBlockVector(this);
	}

	/**
	 * Creates a 2D vector by dropping the Y component from this vector.
	 *
	 * @return a new {@code Vector2D}
	 */
	public KVector2D toVector2D() {
		return new KVector2D(x, z);
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof KVector)) {
			return false;
		}

		KVector other = (KVector) obj;
		return other.x == this.x && other.y == this.y && other.z == this.z;
	}

	@Override
	public int compareTo(@Nullable KVector other) {
		if (other == null) {
			throw new IllegalArgumentException("null not supported");
		}
		if (y != other.y)
			return Double.compare(y, other.y);
		if (z != other.z)
			return Double.compare(z, other.z);
		if (x != other.x)
			return Double.compare(x, other.x);
		return 0;
	}

	@Override
	public int hashCode() {
		int hash = 7;

		hash = 79 * hash + (int) (Double.doubleToLongBits(this.x) ^ (Double.doubleToLongBits(this.x) >>> 32));
		hash = 79 * hash + (int) (Double.doubleToLongBits(this.y) ^ (Double.doubleToLongBits(this.y) >>> 32));
		hash = 79 * hash + (int) (Double.doubleToLongBits(this.z) ^ (Double.doubleToLongBits(this.z) >>> 32));
		return hash;
	}

	@Override
	public String toString() {
		return "(" + x + ", " + y + ", " + z + ")";
	}

	/**
	 * Gets the minimum components of two vectors.
	 *
	 * @param v1 the first vector
	 * @param v2 the second vector
	 * @return minimum
	 */
	public static KVector getMinimum(KVector v1, KVector v2) {
		return new KVector(Math.min(v1.x, v2.x), Math.min(v1.y, v2.y), Math.min(v1.z, v2.z));
	}

	/**
	 * Gets the maximum components of two vectors.
	 *
	 * @param v1 the first vector
	 * @param v2 the second vector
	 * @return maximum
	 */
	public static KVector getMaximum(KVector v1, KVector v2) {
		return new KVector(Math.max(v1.x, v2.x), Math.max(v1.y, v2.y), Math.max(v1.z, v2.z));
	}

	/**
	 * Gets the midpoint of two vectors.
	 *
	 * @param v1 the first vector
	 * @param v2 the second vector
	 * @return maximum
	 */
	public static KVector getMidpoint(KVector v1, KVector v2) {
		return new KVector((v1.x + v2.x) / 2, (v1.y + v2.y) / 2, (v1.z + v2.z) / 2);
	}
}