package keelfy.klibrary.common;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import keelfy.klibrary.events.KBlockEvent.BlockIgniteEvent;
import keelfy.klibrary.events.PlayerMoveEvent;
import keelfy.klibrary.utils.*;
import net.minecraft.init.Blocks;
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

	@SubscribeEvent
	public void testIgnite(BlockIgniteEvent event) {
		event.setCanceled(true);
	}

//	@SubscribeEvent
//	public void testBurn(BlockBurnEvent event) {
//		event.setCanceled(true);
//	}

	@SubscribeEvent
	public void playerMove(PlayerMoveEvent event) {
		if (!event.getPlayer().capabilities.isCreativeMode) {
			KBlock to = new KLocation(event.getPlayer()).getLocatedBlock();
			KBlock from = event.getPrevious().getLocatedBlock();

			if (to.isBlockEqual(Blocks.cobblestone)) {
				event.setCanceled(true);
			}
		}
	}
}
