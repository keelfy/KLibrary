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
				entity.setDead();
				entity.worldObj.setBlock(blockFrom.getX(), blockFrom.getY(), blockFrom.getZ(), blockFrom.getBlock());
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
}
