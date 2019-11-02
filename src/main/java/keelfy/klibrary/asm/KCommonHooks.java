package keelfy.klibrary.asm;

import java.util.*;

import gloomyfolken.hooklib.asm.*;
import keelfy.klibrary.events.block.*;
import keelfy.klibrary.events.block.KBlockEvent.BlockBurnEvent;
import keelfy.klibrary.events.entity.EntityInteractBlockEvent;
import keelfy.klibrary.events.entity.EntityInteractBlockEvent.InteractType;
import keelfy.klibrary.utils.*;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * @author keelfy
 */
public final class KCommonHooks {

	/**
	 * Calling {@link EntityInteractBlockEvent}.
	 */
	@Hook(returnCondition = ReturnCondition.ON_TRUE)
	public static boolean onEntityCollidedWithBlock(BlockTripWire block, World world, int x, int y, int z, Entity entity) {
		return MinecraftForge.EVENT_BUS.post(new EntityInteractBlockEvent(new KBlock(world, x, y, z, block), entity, InteractType.PHYSICAL));
	}

	/**
	 * Calling {@link EntityInteractBlockEvent}.
	 */
	@Hook(returnCondition = ReturnCondition.ON_TRUE)
	public static boolean onEntityCollidedWithBlock(BlockBasePressurePlate block, World world, int x, int y, int z, Entity entity) {
		return MinecraftForge.EVENT_BUS.post(new EntityInteractBlockEvent(new KBlock(world, x, y, z, block), entity, InteractType.PHYSICAL));
	}

	/**
	 * Calling {@link LiquidFlowEvent}
	 */
	@Hook(returnCondition = ReturnCondition.ON_TRUE)
	public static boolean func_149807_p(BlockDynamicLiquid liquid, World world, int x, int y, int z) {
		return MinecraftForge.EVENT_BUS.post(new LiquidFlowEvent(new KBlock(world, x, y, z), liquid));
	}

	/**
	 * Calling {@link BlockBurnEvent}.
	 */
	@Hook
	public static void updateTick(BlockFire block, World world, int x, int y, int z, Random rand) {
		BlockBurnEvent blockBurnEvent = new BlockBurnEvent(new KBlock(new KLocation(world, x, y, z)));
		if (MinecraftForge.EVENT_BUS.post(blockBurnEvent)) {
			world.setBlockToAir(x, y, z);
			return;
		}
	}

	/**
	 * Calling {@link BlockIgniteEvent}.
	 */
	@Hook(returnCondition = ReturnCondition.ON_TRUE)
	public static boolean setBlock(World world, int x, int y, int z, Block block, int meta, int flags) {
		if (block instanceof BlockFire) {
			BlockIgniteEvent blockIgniteEvent = new BlockIgniteEvent(new KBlock(new KLocation(world, x, y, z), block, meta));
			return MinecraftForge.EVENT_BUS.post(blockIgniteEvent);
		}
		return false;
	}

	/**
	 * Calling {@link PistonEvent.Extend} and {@link PistonEvent.Retract}.
	 */
	@Hook(returnCondition = ReturnCondition.ALWAYS)
	public static void updatePistonState(BlockPistonBase piston, World world, int x, int y, int z) {
		int meta = world.getBlockMetadata(x, y, z);
		int orientation = piston.getPistonOrientation(meta);

		if (orientation != 7) {
			boolean isPowered = piston.isIndirectlyPowered(world, x, y, z, orientation);
			ForgeDirection direction = ForgeDirection.getOrientation(orientation);
			KBlock pistonBlock = new KBlock(new KLocation(world, x, y, z), piston, meta);
			KBlock interactedBlock = new KBlock(new KLocation(world, x + direction.offsetX, y + direction.offsetY, z + direction.offsetZ));

			if (isPowered && !piston.isExtended(meta)) {
				PistonEvent call = new PistonEvent.Extend(pistonBlock, direction, piston.isSticky, Arrays.asList(interactedBlock));
				if (MinecraftForge.EVENT_BUS.post(call))
					return;

				if (piston.canExtend(world, x, y, z, orientation)) {
					world.addBlockEvent(x, y, z, piston, 0, orientation);
				}
			} else if (!isPowered && piston.isExtended(meta)) {
				PistonEvent call = new PistonEvent.Retract(pistonBlock, direction, piston.isSticky, interactedBlock.getRelative(direction, 1));
				if (MinecraftForge.EVENT_BUS.post(call))
					return;

				world.setBlockMetadataWithNotify(x, y, z, orientation, 2);
				world.addBlockEvent(x, y, z, piston, 1, orientation);
			}
		}
	}

	/** {@link PlayerEmptyBucketEvent} */
//	@Hook(returnCondition = ReturnCondition.ALWAYS)
//	public static ItemStack onItemRightClick(ItemBucket bucket, ItemStack stack, World world, EntityPlayer player) {
//		boolean flag = bucket.isFull == Blocks.air;
//		MovingObjectPosition mop = KServerUtils.getMovingObjectPositionFromPlayer(world, player, flag);
//
//		if (mop == null) {
//			return stack;
//		} else {
//			FillBucketEvent event = new FillBucketEvent(player, stack, world, mop);
//			if (MinecraftForge.EVENT_BUS.post(event)) {
//				return stack;
//			}
//
//			if (event.getResult() == Event.Result.ALLOW) {
//				if (player.capabilities.isCreativeMode) {
//					return stack;
//				}
//
//				if (--stack.stackSize <= 0) {
//					return event.result;
//				}
//
//				if (!player.inventory.addItemStackToInventory(event.result)) {
//					player.dropPlayerItemWithRandomChoice(event.result, false);
//				}
//
//				return stack;
//			}
//
//			if (mop.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
//				int i = mop.blockX;
//				int j = mop.blockY;
//				int k = mop.blockZ;
//
//				if (!world.canMineBlock(player, i, j, k)) {
//					return stack;
//				}
//
//				if (flag) {
//					if (!player.canPlayerEdit(i, j, k, mop.sideHit, stack)) {
//						return stack;
//					}
//
//					Material material = world.getBlock(i, j, k).getMaterial();
//					int l = world.getBlockMetadata(i, j, k);
//
//					if (material == Material.water && l == 0) {
//						world.setBlockToAir(i, j, k);
//						return bucket.func_150910_a(stack, player, Items.water_bucket);
//					}
//
//					if (material == Material.lava && l == 0) {
//						world.setBlockToAir(i, j, k);
//						return bucket.func_150910_a(stack, player, Items.lava_bucket);
//					}
//				} else {
//					if (bucket.isFull == Blocks.air) {
//						return new ItemStack(Items.bucket);
//					}
//
//					if (mop.sideHit == 0) {
//						--j;
//					}
//
//					if (mop.sideHit == 1) {
//						++j;
//					}
//
//					if (mop.sideHit == 2) {
//						--k;
//					}
//
//					if (mop.sideHit == 3) {
//						++k;
//					}
//
//					if (mop.sideHit == 4) {
//						--i;
//					}
//
//					if (mop.sideHit == 5) {
//						++i;
//					}
//
//					if (!player.canPlayerEdit(i, j, k, mop.sideHit, stack)) {
//						return stack;
//					}
//
//					PlayerEmptyBucketEvent call = new PlayerEmptyBucketEvent(player, new KBlock(world, i, j, k), bucket.isFull);
//					if (!MinecraftForge.EVENT_BUS.post(call)) {
//						if (bucket.tryPlaceContainedLiquid(world, i, j, k) && !player.capabilities.isCreativeMode) {
//							return new ItemStack(Items.bucket);
//						}
//					}
//				}
//			}
//
//			return stack;
//		}
//	}
}
