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
	protected ForgeDirection face;

	protected final String blockName;
	protected final int hashCode;

	public KBlock(KLocation location, Block block, int meta) {
		this.location = location;
		this.block = block;
		this.meta = meta;
		this.face = ForgeDirection.UNKNOWN;
		this.hashCode = (this.blockName = Block.blockRegistry.getNameForObject(block) + "@" + meta).hashCode();
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

	public void setFace(ForgeDirection direction) {
		this.face = direction;
	}

	public ForgeDirection getFace() {
		return face;
	}

	public TileEntity getTileEntity() {
		return this.getLocation().getWorld().getTileEntity(getX(), getY(), getZ());
	}

	public void setBlock(Block block) {
		this.getWorld().setBlock(this.getX(), this.getY(), this.getZ(), block);
	}

	public KBlock getRelative(ForgeDirection direction, int distance) {
		return new KBlock(new KLocation(location.getWorld(), getX() + direction.offsetX * distance, getY() + direction.offsetY * distance, getZ() + direction.offsetZ * distance));
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

	@Override
	public String toString() {
		return super.toString();
	}

	@Override
	public int hashCode() {
		return this.hashCode;
	}

	@Override
	@SuppressWarnings("unlikely-arg-type")
	public boolean equals(Object obj) {
		if (obj == null)
			return false;

		if (obj instanceof Block) {
			return Block.isEqualTo(block, (Block) obj);
		} else if (obj instanceof KLocation) {
			KLocation loc = (KLocation) obj;

			return this.location.getPosition().equals(loc.getPosition()) && this.location.getWorld().getWorldInfo().getWorldName().equals(loc.getWorld().getWorldInfo().getWorldName())
					&& this.location.getWorld().provider.dimensionId == loc.getWorld().provider.dimensionId;
		} else if (obj instanceof KBlock) {
			KBlock kBlock = (KBlock) obj;

			return this.equals(kBlock.block) && this.equals(kBlock.location) && this.meta == kBlock.meta;
		}
		return false;
	}
}
