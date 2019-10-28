package keelfy.klibrary.common;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.*;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import keelfy.klibrary.common.player.KPlayerProperties;
import keelfy.klibrary.events.block.HangingEvent;
import keelfy.klibrary.events.entity.EntityMoveEvent;
import keelfy.klibrary.events.entity.player.*;
import keelfy.klibrary.utils.*;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.player.*;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;

/**
 * @author keelfy
 */
public enum KCommonEvents {
	INSTANCE;

	public static void register() {
		MinecraftForge.EVENT_BUS.register(INSTANCE);
		FMLCommonHandler.instance().bus().register(INSTANCE);
	}

	@SubscribeEvent
	public void entityCostructing(EntityConstructing event) {
		if (event.entity instanceof EntityPlayer)
			KPlayerProperties.construct((EntityPlayer) event.entity);
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onPlayerUpdate(TickEvent.PlayerTickEvent event) {
		if (event.player.worldObj.isRemote)
			return;

		EntityPlayerMP player = (EntityPlayerMP) event.player;
		KPlayerProperties data = KPlayerProperties.get(player);

		if (player.posX != player.prevPosX || player.posY != player.prevPosY || player.posZ != player.prevPosZ || player.rotationYaw != player.prevRotationYaw || player.rotationPitch != player.prevRotationPitch) {
			KLocation from = new KLocation(player.worldObj, player.prevPosX, player.prevPosY, player.prevPosZ, player.prevRotationYaw, player.prevRotationPitch);
			KLocation to = new KLocation(player);
			EntityMoveEvent entityMoveEvent = new EntityMoveEvent(player, from, to);

			if (MinecraftForge.EVENT_BUS.post(entityMoveEvent)) {
				player.playerNetServerHandler.setPlayerLocation(to.getPosition().getX(), to.getPosition().getY(), to.getPosition().getZ(), to.getYaw(), to.getPitch());
			}
		}

		if (event.phase == Phase.START) {
			if (player.theItemInWorldManager.getGameType() != data.getLastTickGameMode()) {
				PlayerGameModeChangeEvent call = new PlayerGameModeChangeEvent(player, data.getLastTickGameMode(), player.theItemInWorldManager.getGameType());

				if (MinecraftForge.EVENT_BUS.post(call)) {
					player.setGameType(data.getLastTickGameMode());
					return;
				}
			}
		} else {
			KPlayerProperties.get(player).setLastTickGameMode(player.theItemInWorldManager.getGameType());
		}
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onPlayerInteract(PlayerInteractEvent event) {
		if (event.world.isRemote)
			return;

		EntityPlayerMP player = (EntityPlayerMP) event.entityPlayer;
		ItemStack stack = player.getHeldItem();
		KBlock clicked = new KBlock(event.world, event.x, event.y, event.z);

		if (event.action == Action.RIGHT_CLICK_BLOCK) {
			if (stack != null) {
				if ((stack.getItem() == Items.item_frame || stack.getItem() == Items.painting)) {
					HangingEvent.Place call = new HangingEvent.Place(null, clicked, ForgeDirection.values()[event.face], player);
					if (MinecraftForge.EVENT_BUS.post(call)) {
						event.setCanceled(true);
						return;
					}
				}
			}

			if (clicked.isBlockEqual(Blocks.bed)) {
				PlayerBedEnterEvent call = new PlayerBedEnterEvent(player, clicked);
				if (MinecraftForge.EVENT_BUS.post(call)) {
					event.setCanceled(true);
					return;
				}
			}
		}
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onPlayerAttackEntity(AttackEntityEvent event) {
		if (event.entityPlayer.worldObj.isRemote)
			return;

		if (event.target instanceof EntityHanging) {
			if (MinecraftForge.EVENT_BUS.post(new HangingEvent.Break(event.target, DamageSource.causePlayerDamage(event.entityPlayer)))) {
				event.setCanceled(true);
			}
		}
	}

}
