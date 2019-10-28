package keelfy.klibrary.server;

import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import keelfy.klibrary.events.block.*;
import net.minecraftforge.common.MinecraftForge;

/**
 * @author keelfy
 */
public enum KServerEvents {
	INSTANCE;

	public static void register() {
		MinecraftForge.EVENT_BUS.register(INSTANCE);
	}

	@SubscribeEvent
	public void pistonExtend(PistonEvent.Extend event) {}

	@SubscribeEvent
	public void blockPushTest(KBlockEvent.PushTest event) {
		int x = event.getLocatedBlock().getX();
		int y = event.getLocatedBlock().getY();
		int z = event.getLocatedBlock().getZ();

		if (x == 132 && y == 70 && z == 35) {
			event.setResult(Result.DENY);
		}
	}

	@SubscribeEvent
	public void pistonRetract(PistonEvent.Retract event) {}
}
