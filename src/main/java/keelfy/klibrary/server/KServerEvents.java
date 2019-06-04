package keelfy.klibrary.server;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import keelfy.klibrary.events.*;
import keelfy.klibrary.utils.KLocation;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.common.MinecraftForge;

/**
 * @author keelfy
 */
public enum KServerEvents {
	INSTANCE;

	public static void register() {
		MinecraftForge.EVENT_BUS.register(INSTANCE);
		FMLCommonHandler.instance().bus().register(INSTANCE);
	}

	@SubscribeEvent
	public void onPlayerUpdate(TickEvent.PlayerTickEvent event) {
		if (event.phase == Phase.START)
			return;

		EntityPlayerMP player = (EntityPlayerMP) event.player;

		if (player.posX != player.lastTickPosX || player.posY != player.lastTickPosY || player.posZ != player.lastTickPosZ) {
			KPlayerEvent call = new PlayerMoveEvent(player, new KLocation(player));

			if (MinecraftForge.EVENT_BUS.post(call)) {
				player.playerNetServerHandler.setPlayerLocation(player.lastTickPosX, player.lastTickPosY, player.lastTickPosZ, player.prevCameraYaw, player.prevCameraPitch);
			}
		}
	}

}
