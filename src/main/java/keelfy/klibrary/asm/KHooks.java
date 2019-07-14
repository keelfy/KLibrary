package keelfy.klibrary.asm;

import java.util.Random;

import com.google.common.collect.Lists;

import cpw.mods.fml.common.eventhandler.Event;
import gloomyfolken.hooklib.asm.*;
import keelfy.klibrary.events.block.*;
import keelfy.klibrary.events.block.KBlockEvent.BlockBurnEvent;
import keelfy.klibrary.events.block.PistonEvent.*;
import keelfy.klibrary.events.entity.EntityMoveEvent;
import keelfy.klibrary.events.entity.player.*;
import keelfy.klibrary.server.KServerUtils;
import keelfy.klibrary.utils.*;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.entity.player.FillBucketEvent;

/**
 * @author keelfy
 */
public final class KHooks {

	@Hook(returnCondition = ReturnCondition.ON_TRUE)
	public static boolean moveEntity(Entity entity, double x, double y, double z) {
		EntityMoveEvent call = new EntityMoveEvent(entity, new KVector(x, y, z));
		return MinecraftForge.EVENT_BUS.post(call);
	}

	@Hook
	public static void moveFlying(Entity entity, float x, float y, float z) {
		EntityMoveEvent call = new EntityMoveEvent(entity, new KVector(x, y, z));
		if (MinecraftForge.EVENT_BUS.post(call))
			return;

		if (!call.getTo().equals(call.getFrom())) {
			float f3 = x * x + y * y;

			if (f3 >= 1.0E-4F) {
				f3 = MathHelper.sqrt_float(f3);

				if (f3 < 1.0F) {
					f3 = 1.0F;
				}

				f3 = z / f3;
				x *= f3;
				y *= f3;
				float f4 = MathHelper.sin(entity.rotationYaw * (float) Math.PI / 180.0F);
				float f5 = MathHelper.cos(entity.rotationYaw * (float) Math.PI / 180.0F);
				entity.motionX += x * f5 - y * f4;
				entity.motionZ += y * f5 + x * f4;
			}
		}
	}

	/** {@link LiquidFlowEvent} */
	@Hook(returnCondition = ReturnCondition.ON_TRUE)
	public static boolean func_149807_p(BlockDynamicLiquid liquid, World world, int x, int y, int z) {
		LiquidFlowEvent call = new LiquidFlowEvent(new KBlock(world, x, y, z), liquid);
		return MinecraftForge.EVENT_BUS.post(call);
	}

	/** {@link BlockBurnEvent} */
	@Hook
	public static void updateTick(BlockFire block, World world, int x, int y, int z, Random rand) {
		BlockBurnEvent blockBurnEvent = new BlockBurnEvent(new KBlock(new KLocation(world, x, y, z)));
		if (MinecraftForge.EVENT_BUS.post(blockBurnEvent)) {
			world.setBlockToAir(x, y, z);
			return;
		}
	}

	@Hook(returnCondition = ReturnCondition.ON_TRUE)
	public static boolean setBlock(World world, int x, int y, int z, Block block, int meta, int flags) {
		if (block instanceof BlockFire) {
			BlockIgniteEvent blockIgniteEvent = new BlockIgniteEvent(new KBlock(new KLocation(world, x, y, z), block, meta));
			return MinecraftForge.EVENT_BUS.post(blockIgniteEvent);
		}
		return false;
	}

	/** {@link Extend} and {@link Retract} */
	@Hook(returnCondition = ReturnCondition.ALWAYS)
	public static void updatePistonState(BlockPistonBase piston, World world, int x, int y, int z) {
		int meta = world.getBlockMetadata(x, y, z);
		int orientation = piston.getPistonOrientation(meta);

		if (orientation != 7) {
			boolean isPowered = piston.isIndirectlyPowered(world, x, y, z, orientation);
			KBlock block = new KBlock(new KLocation(world, x, y, z), piston, meta);
			ForgeDirection direction = ForgeDirection.values()[orientation];

			if (isPowered && !piston.isExtended(meta)) {
				PistonEvent call = new PistonEvent.Extend(block, direction, piston.isSticky, Lists.newArrayList());
				if (MinecraftForge.EVENT_BUS.post(call)) {
					return;
				}

				if (piston.canExtend(world, x, y, z, orientation)) {
					world.addBlockEvent(x, y, z, piston, 0, orientation);
				}
			} else if (!isPowered && piston.isExtended(meta)) {
				PistonEvent call = new PistonEvent.Retract(block, direction, piston.isSticky, block.getRelative(direction, 1));
				if (MinecraftForge.EVENT_BUS.post(call)) {
					return;
				}

				world.setBlockMetadataWithNotify(x, y, z, orientation, 2);
				world.addBlockEvent(x, y, z, piston, 1, orientation);
			}
		}
	}

	/** {@link PlayerEmptyBucketEvent} */
	@Hook(returnCondition = ReturnCondition.ALWAYS)
	public static ItemStack onItemRightClick(ItemBucket bucket, ItemStack stack, World world, EntityPlayer player) {
		boolean flag = bucket.isFull == Blocks.air;
		MovingObjectPosition movingobjectposition = KServerUtils.getMovingObjectPositionFromPlayer(world, player, flag);

		if (movingobjectposition == null) {
			return stack;
		} else {
			FillBucketEvent event = new FillBucketEvent(player, stack, world, movingobjectposition);
			if (MinecraftForge.EVENT_BUS.post(event)) {
				return stack;
			}

			if (event.getResult() == Event.Result.ALLOW) {
				if (player.capabilities.isCreativeMode) {
					return stack;
				}

				if (--stack.stackSize <= 0) {
					return event.result;
				}

				if (!player.inventory.addItemStackToInventory(event.result)) {
					player.dropPlayerItemWithRandomChoice(event.result, false);
				}

				return stack;
			}

			if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
				int i = movingobjectposition.blockX;
				int j = movingobjectposition.blockY;
				int k = movingobjectposition.blockZ;

				if (!world.canMineBlock(player, i, j, k)) {
					return stack;
				}

				if (flag) {
					if (!player.canPlayerEdit(i, j, k, movingobjectposition.sideHit, stack)) {
						return stack;
					}

					Material material = world.getBlock(i, j, k).getMaterial();
					int l = world.getBlockMetadata(i, j, k);

					if (material == Material.water && l == 0) {
						world.setBlockToAir(i, j, k);
						return bucket.func_150910_a(stack, player, Items.water_bucket);
					}

					if (material == Material.lava && l == 0) {
						world.setBlockToAir(i, j, k);
						return bucket.func_150910_a(stack, player, Items.lava_bucket);
					}
				} else {
					if (bucket.isFull == Blocks.air) {
						return new ItemStack(Items.bucket);
					}

					if (movingobjectposition.sideHit == 0) {
						--j;
					}

					if (movingobjectposition.sideHit == 1) {
						++j;
					}

					if (movingobjectposition.sideHit == 2) {
						--k;
					}

					if (movingobjectposition.sideHit == 3) {
						++k;
					}

					if (movingobjectposition.sideHit == 4) {
						--i;
					}

					if (movingobjectposition.sideHit == 5) {
						++i;
					}

					if (!player.canPlayerEdit(i, j, k, movingobjectposition.sideHit, stack)) {
						return stack;
					}

					PlayerEmptyBucketEvent call = new PlayerEmptyBucketEvent(player, new KBlock(world, i, j, k), bucket.isFull);
					if (!MinecraftForge.EVENT_BUS.post(call)) {
						if (bucket.tryPlaceContainedLiquid(world, i, j, k) && !player.capabilities.isCreativeMode) {
							return new ItemStack(Items.bucket);
						}
					}
				}
			}

			return stack;
		}
	}

	/** {@link SpawnEggEvent} */
	@Hook(returnCondition = ReturnCondition.ALWAYS)
	public static ItemStack onItemRightClick(ItemMonsterPlacer item, ItemStack stack, World world, EntityPlayer player) {
		if (world.isRemote)
			return stack;

		MovingObjectPosition movingobjectposition = KServerUtils.getMovingObjectPositionFromPlayer(world, player, true);

		if (movingobjectposition == null) {
			return stack;
		} else {
			if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
				int i = movingobjectposition.blockX;
				int j = movingobjectposition.blockY;
				int k = movingobjectposition.blockZ;

				if (!world.canMineBlock(player, i, j, k)) {
					return stack;
				}

				if (!player.canPlayerEdit(i, j, k, movingobjectposition.sideHit, stack)) {
					return stack;
				}

				if (world.getBlock(i, j, k) instanceof BlockLiquid) {
					int entityId = Integer.valueOf(stack.getItemDamage());

					if (!EntityList.entityEggs.containsKey(entityId))
						return stack;

					SpawnEggEvent call = new SpawnEggEvent(player, new KLocation(world, i, j, k), EntityList.getClassFromID(entityId), stack);
					if (MinecraftForge.EVENT_BUS.post(call))
						return stack;

					Entity entity = item.spawnCreature(world, entityId, i, j, k);

					if (entity != null) {
						if (entity instanceof EntityLivingBase && stack.hasDisplayName()) {
							((EntityLiving) entity).setCustomNameTag(stack.getDisplayName());
						}

						if (!player.capabilities.isCreativeMode) {
							--stack.stackSize;
						}
					}
				}
			}

			return stack;
		}
	}

	@Hook(returnCondition = ReturnCondition.ALWAYS)
	public static boolean onItemUse(ItemMonsterPlacer item, ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int face, float p_77648_8_, float p_77648_9_, float p_77648_10_) {
		if (world.isRemote) {
			return true;
		} else {
			Block block = world.getBlock(x, y, z);
			x += Facing.offsetsXForSide[face];
			y += Facing.offsetsYForSide[face];
			z += Facing.offsetsZForSide[face];
			double d0 = 0.0D;

			if (face == 1 && block.getRenderType() == 11) {
				d0 = 0.5D;
			}

			int entityId = Integer.valueOf(stack.getItemDamage());

			if (!EntityList.entityEggs.containsKey(entityId))
				return false;

			SpawnEggEvent call = new SpawnEggEvent(player, new KLocation(world, x + 0.5D, y + d0, z + 0.5D), EntityList.getClassFromID(entityId), stack);
			if (MinecraftForge.EVENT_BUS.post(call))
				return false;

			Entity entity = item.spawnCreature(world, entityId, x + 0.5D, y + d0, z + 0.5D);

			if (entity != null) {
				if (entity instanceof EntityLivingBase && stack.hasDisplayName()) {
					((EntityLiving) entity).setCustomNameTag(stack.getDisplayName());
				}

				if (!player.capabilities.isCreativeMode) {
					--stack.stackSize;
				}
			}

			return true;
		}
	}
}
