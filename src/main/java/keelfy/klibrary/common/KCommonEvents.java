package keelfy.klibrary.common;

import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraftforge.common.MinecraftForge;

/**
 * @author keelfy
 */
public enum KCommonEvents {
	INSTANCE;

	public static void register() {
		MinecraftForge.EVENT_BUS.register(INSTANCE);
		FMLCommonHandler.instance().bus().register(INSTANCE);
	}

//	@SubscribeEvent(priority = EventPriority.HIGHEST)
//	public void onPlayerInteract(PlayerInteractEvent event) {
//		if (event.world.isRemote)
//			return;
//
//		EntityPlayerMP player = (EntityPlayerMP) event.entityPlayer;
//		ItemStack stack = player.getHeldItem();
//		KBlock clicked = new KBlock(event.world, event.x, event.y, event.z);
//
//		if (event.action == Action.RIGHT_CLICK_BLOCK) {
//			if (stack != null) {
//				if ((stack.getItem() == Items.item_frame || stack.getItem() == Items.painting)) {
//					HangingEvent.Place call = new HangingEvent.Place(null, clicked, ForgeDirection.values()[event.face], player);
//					if (MinecraftForge.EVENT_BUS.post(call)) {
//						event.setCanceled(true);
//						return;
//					}
//				}
//			}
//
//			if (clicked.isBlockEqual(Blocks.bed)) {
//				PlayerBedEnterEvent call = new PlayerBedEnterEvent(player, clicked);
//				if (MinecraftForge.EVENT_BUS.post(call)) {
//					event.setCanceled(true);
//					return;
//				}
//			}
//		}
//	}

//	@SubscribeEvent(priority = EventPriority.HIGHEST)
//	public void onPlayerAttackEntity(AttackEntityEvent event) {
//		if (event.entityPlayer.worldObj.isRemote)
//			return;
//
//		if (event.target instanceof EntityHanging) {
//			if (MinecraftForge.EVENT_BUS.post(new HangingEvent.Break(event.target, DamageSource.causePlayerDamage(event.entityPlayer)))) {
//				event.setCanceled(true);
//			}
//		}
//	}

}
