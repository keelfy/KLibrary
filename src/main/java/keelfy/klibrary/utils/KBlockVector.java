package keelfy.klibrary.utils;

/**
 * Extension of Vector that supports being compared as ints (for accuracy).
 * 
 * @author sk89q
 * @author keelfy
 */
public class KBlockVector extends KVector {

	public KBlockVector() {
		super();
	}

	/**
	 * Construct the Vector object.
	 *
	 * @param pt
	 */
	public KBlockVector(KVector pt) {
		super(pt);
	}

	/**
	 * Construct the Vector object.
	 *
	 * @param pt
	 */
	public KBlockVector(int x, int y, int z) {
		super(x, y, z);
	}

	/**
	 * Construct the Vector object.
	 *
	 * @param pt
	 */
	public KBlockVector(float x, float y, float z) {
		super(x, y, z);
	}

	/**
	 * Construct the Vector object.
	 *
	 * @param pt
	 */
	public KBlockVector(double x, double y, double z) {
		super(x, y, z);
	}

	/**
	 * Checks if another object is equivalent.
	 *
	 * @param obj
	 * @return whether the other object is equivalent
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof KVector)) {
			return false;
		}
		KVector other = (KVector) obj;
		return (int) other.x == (int) this.x && (int) other.y == (int) this.y && (int) other.z == (int) this.z;

	}

	/**
	 * Gets the hash code.
	 *
	 * @return hash code
	 */
	@Override
	public int hashCode() {
		return (Integer.valueOf((int) x).hashCode() >> 13) ^ (Integer.valueOf((int) y).hashCode() >> 7) ^ Integer.valueOf((int) z).hashCode();
	}
}
