package keelfy.klibrary.utils;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * @author keelfy
 */
public class KBlock {

	protected KLocation location;
	protected Block block;
	protected int meta;

	public KBlock(KLocation location, Block block, int meta) {
		this.location = location;
		this.block = block;
		this.meta = meta;
	}

	public KBlock(KLocation location, Block block) {
		this(location, block, location.getWorld().getBlockMetadata(location.getPosition().getBlockX(), location.getPosition().getBlockY(), location.getPosition().getBlockZ()));
	}

	public KBlock(KLocation location) {
		this(location, location.getWorld().getBlock(location.getPosition().getBlockX(), location.getPosition().getBlockY(), location.getPosition().getBlockZ()));
	}

	public KBlock(World world, int x, int y, int z, Block block, int meta) {
		this(new KLocation(world, x, y, z), block, meta);
	}

	public KBlock(World world, int x, int y, int z, Block block) {
		this(new KLocation(world, x, y, z), block);
	}

	public KBlock(World world, int x, int y, int z) {
		this(new KLocation(world, x, y, z));
	}

	public KBlock(KBlock block) {
		this(block.location, block.block, block.meta);
	}

	public Item getAsItem() {
		return Item.getItemFromBlock(block);
	}

	public boolean isBlockEqual(Block blockToCompare) {
		return Block.isEqualTo(this.block, blockToCompare);
	}

	public boolean isAir() {
		return isBlockEqual(Blocks.air);
	}

	public TileEntity getTileEntity() {
		return this.getLocation().getWorld().getTileEntity(getX(), getY(), getZ());
	}

	public void setBlock(Block block) {
		this.getWorld().setBlock(this.getX(), this.getY(), this.getZ(), block);
	}

	public KBlock getRelative(ForgeDirection face, int distance) {
		if (distance == 0 || face == ForgeDirection.UNKNOWN)
			return this;

		int x = location.getPosition().getBlockX();
		int y = location.getPosition().getBlockY();
		int z = location.getPosition().getBlockZ();

		switch (face) {
		case WEST:
		case EAST:
			x = x + face.offsetX + (int) Math.copySign(distance, face.offsetX);
			break;
		case UP:
		case DOWN:
			y = y + face.offsetY + (int) Math.copySign(distance, face.offsetY);
			break;
		case NORTH:
		case SOUTH:
			z = z + face.offsetZ + (int) Math.copySign(distance, face.offsetZ);
			break;
		case UNKNOWN:
			break;
		}
		return new KBlock(new KLocation(location.world, x, y, z));
	}

	public KBlock getRelative(ForgeDirection direction) {
		return this.getRelative(direction, 1);
	}

	public World getWorld() {
		return this.location.getWorld();
	}

	public int getX() {
		return this.location.getPosition().getBlockX();
	}

	public int getY() {
		return this.location.getPosition().getBlockY();
	}

	public int getZ() {
		return this.location.getPosition().getBlockZ();
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
