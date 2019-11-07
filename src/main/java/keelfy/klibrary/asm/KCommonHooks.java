package keelfy.klibrary.asm;

import java.util.Random;

import gloomyfolken.hooklib.asm.*;
import keelfy.klibrary.events.block.*;
import keelfy.klibrary.events.block.KBlockEvent.BlockBurnEvent;
import keelfy.klibrary.events.entity.EntityInteractBlockEvent;
import keelfy.klibrary.events.entity.EntityInteractBlockEvent.InteractType;
import keelfy.klibrary.events.entity.player.*;
import keelfy.klibrary.utils.*;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAIBreakDoor;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

/**
 * @author keelfy
 */
public final class KCommonHooks {

	/**
	 * Calling {@link EntityInteractBlockEvent} from {@link EntityAIBreakDoor}.
	 */
	@Hook(injectOnExit = true, returnCondition = ReturnCondition.ALWAYS)
	public static boolean shouldExecute(EntityAIBreakDoor ai, @Hook.ReturnValue boolean shouldExecute) {
		return shouldExecute ? !MinecraftForge.EVENT_BUS.post(new EntityInteractBlockEvent(new KBlock(ai.theEntity.worldObj, ai.entityPosX, ai.entityPosY, ai.entityPosZ), ai.theEntity, InteractType.BREAK)) : false;
	}

	/**
	 * Calling {@link BlockFromToEvent} from {@link BlockFalling}.
	 */
	@Hook(returnCondition = ReturnCondition.ON_TRUE)
	public static boolean func_149830_m(BlockFalling block, World world, int x, int y, int z) {
		KBlock blockFrom = new KBlock(world, x, y, z, block);
		KBlock blockTo = new KBlock(world, x, y - 1, z);
		return y >= 0 && MinecraftForge.EVENT_BUS.post(new BlockFromToEvent(blockFrom, blockTo));
	}

	/**
	 * Calling {@link PlayerEditCheckEvent} from {@link EntityPlayer}.
	 */
	@Hook(injectOnExit = true, returnCondition = ReturnCondition.ALWAYS)
	public static boolean canPlayerEdit(EntityPlayer player, int x, int y, int z, int sideOfHit, ItemStack itemStack, @Hook.ReturnValue boolean allowEdit) {
		return allowEdit ? !MinecraftForge.EVENT_BUS.post(new PlayerEditCheckEvent(player, x, y, z, sideOfHit, itemStack)) : false;
	}

	/**
	 * Calling {@link PlayerMineCheckEvent} from {@link World}.
	 */
	@Hook(injectOnExit = true, returnCondition = ReturnCondition.ALWAYS)
	public static boolean canMineBlock(World world, EntityPlayer player, int x, int y, int z, @Hook.ReturnValue boolean allowMine) {
		return allowMine ? !MinecraftForge.EVENT_BUS.post(new PlayerMineCheckEvent(player, x, y, z)) : false;
	}

	/**
	 * Calling {@link BlockFromToEvent} from {@link EntityFallingBlock}.
	 */
	@Hook(returnCondition = ReturnCondition.ON_TRUE)
	public static boolean onUpdate(EntityFallingBlock entity) {
		KVector prevPos = new KVector(entity.prevPosX, entity.prevPosY, entity.prevPosZ);
		KVector curPos = new KVector(entity);

		if (prevPos.getBlockX() != curPos.getBlockY() || prevPos.getBlockY() != curPos.getBlockY() || prevPos.getBlockZ() != curPos.getBlockZ()) {
			KBlock blockFrom = new KBlock(new KLocation(entity.worldObj, prevPos), entity.func_145805_f(), entity.field_145812_b);
			KBlock blockTo = new KBlock(new KLocation(entity.worldObj, curPos), entity.func_145805_f(), entity.field_145812_b);

			if (blockTo.getY() >= 0 && MinecraftForge.EVENT_BUS.post(new BlockFromToEvent(blockFrom, blockTo))) {
//				if (entity.worldObj.getBlock(blockTo.getX(), blockTo.getY(), blockTo.getZ()) != Blocks.piston_extension) {
				entity.setDead();
				entity.worldObj.setBlock(blockFrom.getX(), blockFrom.getY(), blockFrom.getZ(), blockFrom.getBlock());

//					if (entity.worldObj.setBlock(blockFrom.getX(), blockFrom.getY(), blockFrom.getZ(), entity.func_145805_f(), entity.field_145814_a, 3)) {
//						if (entity.func_145805_f() instanceof BlockFalling) {
//							((BlockFalling) entity.func_145805_f()).func_149828_a(entity.worldObj, blockTo.getX(), blockTo.getY(), blockTo.getZ(), entity.field_145814_a);
//						}
//
//						if (entity.field_145810_d != null && entity.func_145805_f() instanceof ITileEntityProvider) {
//							TileEntity tileEntity = entity.worldObj.getTileEntity(blockTo.getX(), blockTo.getY(), blockTo.getZ());
//
//							if (tileEntity != null) {
//								NBTTagCompound compound = new NBTTagCompound();
//								tileEntity.writeToNBT(compound);
//								Iterator iterator = entity.field_145810_d.func_150296_c().iterator();
//
//								while (iterator.hasNext()) {
//									String s = (String) iterator.next();
//									NBTBase nbtbase = entity.field_145810_d.getTag(s);
//
//									if (!s.equals("x") && !s.equals("y") && !s.equals("z")) {
//										compound.setTag(s, nbtbase.copy());
//									}
//								}
//
//								tileEntity.readFromNBT(compound);
//								tileEntity.markDirty();
//							}
//						}
//					} else if (entity.field_145813_c) {
//						entity.entityDropItem(new ItemStack(entity.func_145805_f(), 1, entity.func_145805_f().damageDropped(entity.field_145814_a)), 0.0F);
//					}
//				}
				return true;
			}

		}
		return false;
	}

	/**
	 * Calling {@link EntityInteractBlockEvent} from {@link BlockFarmland}.
	 */
	@Hook(returnCondition = ReturnCondition.ON_TRUE)
	public static boolean onFallenUpon(BlockFarmland block, World world, int x, int y, int z, Entity entity, float f) {
		return MinecraftForge.EVENT_BUS.post(new EntityInteractBlockEvent(new KBlock(world, x, y, z), entity, InteractType.PHYSICAL));
	}

	/**
	 * Calling {@link EntityInteractBlockEvent} from {@link BlockTripWire}.
	 */
	@Hook(returnCondition = ReturnCondition.ON_TRUE)
	public static boolean onEntityCollidedWithBlock(BlockTripWire block, World world, int x, int y, int z, Entity entity) {
		return MinecraftForge.EVENT_BUS.post(new EntityInteractBlockEvent(new KBlock(world, x, y, z, block), entity, InteractType.PHYSICAL));
	}

	/**
	 * Calling {@link EntityInteractBlockEvent} from {@link BlockBasePressurePlate}.
	 */
	@Hook(returnCondition = ReturnCondition.ON_TRUE)
	public static boolean onEntityCollidedWithBlock(BlockBasePressurePlate block, World world, int x, int y, int z, Entity entity) {
		return MinecraftForge.EVENT_BUS.post(new EntityInteractBlockEvent(new KBlock(world, x, y, z, block), entity, InteractType.PHYSICAL));
	}

	/**
	 * Calling {@link LiquidFlowEvent} from {@link BlockDynamicLiquid}.
	 */
//	@Hook(returnCondition = ReturnCondition.ALWAYS)
//	public static void updateTick(BlockDynamicLiquid block, World world, int x, int y, int z, Random rand) {
//		int metadata = block.func_149804_e(world, x, y, z);
//		byte b0 = 1;
//
//		if (block.getMaterial() == Material.lava && !world.provider.isHellWorld) {
//			b0 = 2;
//		}
//
//		int tickRate = block.tickRate(world);
//		int j1;
//
//		if (metadata > 0) {
//			byte b1 = -100;
//			block.field_149815_a = 0;
//			int l1 = block.func_149810_a(world, x - 1, y, z, b1);
//			l1 = block.func_149810_a(world, x + 1, y, z, l1);
//			l1 = block.func_149810_a(world, x, y, z - 1, l1);
//			l1 = block.func_149810_a(world, x, y, z + 1, l1);
//			j1 = l1 + b0;
//
//			if (j1 >= 8 || l1 < 0) {
//				j1 = -1;
//			}
//
//			if (block.func_149804_e(world, x, y + 1, z) >= 0) {
//				int k1 = block.func_149804_e(world, x, y + 1, z);
//
//				if (k1 >= 8) {
//					j1 = k1;
//				} else {
//					j1 = k1 + 8;
//				}
//			}
//
//			if (block.field_149815_a >= 2 && block.getMaterial() == Material.water) {
//				if (world.getBlock(x, y - 1, z).getMaterial().isSolid()) {
//					j1 = 0;
//				} else if (world.getBlock(x, y - 1, z).getMaterial() == block.getMaterial() && world.getBlockMetadata(x, y - 1, z) == 0) {
//					j1 = 0;
//				}
//			}
//
//			if (block.getMaterial() == Material.lava && metadata < 8 && j1 < 8 && j1 > metadata && rand.nextInt(4) != 0) {
//				tickRate *= 4;
//			}
//
//			if (j1 == metadata) {
//				block.func_149811_n(world, x, y, z);
//			} else {
//				metadata = j1;
//
//				if (j1 < 0) {
//					world.setBlockToAir(x, y, z);
//				} else {
//					world.setBlockMetadataWithNotify(x, y, z, j1, 2);
//					world.scheduleBlockUpdate(x, y, z, block, tickRate);
//					world.notifyBlocksOfNeighborChange(x, y, z, block);
//				}
//			}
//		} else {
//			block.func_149811_n(world, x, y, z);
//		}
//
//		if (block.func_149809_q(world, x, y - 1, z)) {
//			if (block.getMaterial() == Material.lava && world.getBlock(x, y - 1, z).getMaterial() == Material.water) {
//				world.setBlock(x, y - 1, z, Blocks.stone);
//				block.func_149799_m(world, x, y - 1, z);
//				return;
//			}
//
//			if (metadata >= 8) {
//				KBlock blockTo = createKBlock(world, new KVector(x, y - 1, z), metadata);
//
//				if (!MinecraftForge.EVENT_BUS.post(new LiquidFlowEvent(new KBlock(world, x, y, z, block), blockTo))) {
//					block.func_149813_h(world, blockTo.getX(), blockTo.getY(), blockTo.getZ(), blockTo.getMeta());
//				}
//			} else {
//				KBlock blockTo = createKBlock(world, new KVector(x, y - 1, z), metadata + 8);
//
//				if (!MinecraftForge.EVENT_BUS.post(new LiquidFlowEvent(new KBlock(world, x, y, z, block), blockTo))) {
//					block.func_149813_h(world, blockTo.getX(), blockTo.getY(), blockTo.getZ(), blockTo.getMeta());
//				}
//			}
//		} else if (metadata >= 0 && (metadata == 0 || block.func_149807_p(world, x, y - 1, z))) {
//			boolean[] availableDirections = block.func_149808_o(world, x, y, z);
//			j1 = metadata + b0;
//
//			if (metadata >= 8) {
//				j1 = 1;
//			}
//
//			if (j1 >= 8) {
//				return;
//			}
//
//			if (availableDirections[0]) {
//				KBlock blockTo = createKBlock(world, new KVector(x - 1, y, z), j1);
//
//				if (!MinecraftForge.EVENT_BUS.post(new LiquidFlowEvent(new KBlock(world, x, y, z, block), blockTo))) {
//					block.func_149813_h(world, blockTo.getX(), blockTo.getY(), blockTo.getZ(), blockTo.getMeta());
//				}
//			}
//
//			if (availableDirections[1]) {
//				KBlock blockTo = createKBlock(world, new KVector(x + 1, y, z), j1);
//
//				if (!MinecraftForge.EVENT_BUS.post(new LiquidFlowEvent(new KBlock(world, x, y, z, block), blockTo))) {
//					block.func_149813_h(world, blockTo.getX(), blockTo.getY(), blockTo.getZ(), blockTo.getMeta());
//				}
//			}
//
//			if (availableDirections[2]) {
//				KBlock blockTo = createKBlock(world, new KVector(x, y, z - 1), j1);
//
//				if (!MinecraftForge.EVENT_BUS.post(new LiquidFlowEvent(new KBlock(world, x, y, z, block), blockTo))) {
//					block.func_149813_h(world, blockTo.getX(), blockTo.getY(), blockTo.getZ(), blockTo.getMeta());
//				}
//			}
//
//			if (availableDirections[3]) {
//				KBlock blockTo = createKBlock(world, new KVector(x, y, z + 1), j1);
//
//				if (!MinecraftForge.EVENT_BUS.post(new LiquidFlowEvent(new KBlock(world, x, y, z, block), blockTo))) {
//					block.func_149813_h(world, blockTo.getX(), blockTo.getY(), blockTo.getZ(), blockTo.getMeta());
//				}
//			}
//		}
//	}

	private static KBlock createKBlock(World world, KVector target, int metadata) {
		return new KBlock(world, target.getBlockX(), target.getBlockY(), target.getBlockZ(), world.getBlock(target.getBlockX(), target.getBlockY(), target.getBlockZ()), metadata);
	}

	/**
	 * Calling {@link BlockBurnEvent} from {@link BlockFire}.
	 */
	@Hook(returnCondition = ReturnCondition.ON_TRUE)
	public static boolean updateTick(BlockFire block, World world, int x, int y, int z, Random rand) {
		BlockBurnEvent blockBurnEvent = new BlockBurnEvent(new KBlock(new KLocation(world, x, y, z)));
		boolean result = MinecraftForge.EVENT_BUS.post(blockBurnEvent);
		if (result)
			world.setBlockToAir(x, y, z);
		return result;
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
//	@Hook(returnCondition = ReturnCondition.ALWAYS)
//	public static void updatePistonState(BlockPistonBase piston, World world, int x, int y, int z) {
//		int meta = world.getBlockMetadata(x, y, z);
//		int orientation = piston.getPistonOrientation(meta);
//
//		if (orientation != 7) {
//			boolean isPowered = piston.isIndirectlyPowered(world, x, y, z, orientation);
//			ForgeDirection direction = ForgeDirection.getOrientation(orientation);
//			KBlock pistonBlock = new KBlock(new KLocation(world, x, y, z), piston, meta);
//			KBlock interactedBlock = new KBlock(new KLocation(world, x + direction.offsetX, y + direction.offsetY, z + direction.offsetZ));
//
//			if (isPowered && !piston.isExtended(meta)) {
//				PistonEvent call = new PistonEvent.Extend(pistonBlock, direction, piston.isSticky, Arrays.asList(interactedBlock));
//				if (MinecraftForge.EVENT_BUS.post(call))
//					return;
//
//				if (piston.canExtend(world, x, y, z, orientation)) {
//					world.addBlockEvent(x, y, z, piston, 0, orientation);
//				}
//			} else if (!isPowered && piston.isExtended(meta)) {
//				PistonEvent call = new PistonEvent.Retract(pistonBlock, direction, piston.isSticky, interactedBlock.getRelative(direction, 1));
//				if (MinecraftForge.EVENT_BUS.post(call))
//					return;
//
//				world.setBlockMetadataWithNotify(x, y, z, orientation, 2);
//				world.addBlockEvent(x, y, z, piston, 1, orientation);
//			}
//		}
//	}

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
