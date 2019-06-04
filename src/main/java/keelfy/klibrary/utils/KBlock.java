package keelfy.klibrary.utils;

import net.minecraft.block.Block;
import net.minecraft.world.World;

/**
 * @author keelfy
 */
public class KBlock {

	private KLocation location;
	private Block block;
	private int meta;

	public KBlock(KLocation location, Block block, int meta) {
		this.location = location;
		this.block = block;
		this.meta = meta;
	}

	public KBlock(KLocation location, Block block) {
		this(location, block, location.getWorld().getBlockMetadata(location.getBlockX(), location.getBlockY(), location.getBlockZ()));
	}

	public KBlock(KLocation location) {
		this(location, location.getWorld().getBlock(location.getBlockX(), location.getBlockY(), location.getBlockZ()));
	}

	public KBlock(World world, int x, int y, int z) {
		this(new KLocation(world, x, y, z));
	}

	public boolean isBlockEqual(Block blockToCompare) {
		return Block.isEqualTo(this.block, blockToCompare);
	}

	public KLocation getLocation() {
		return location;
	}

	public Block getBlock() {
		return block;
	}

	public int getMeta() {
		return meta;
	}
}
