package keelfy.klibrary.server;

import net.minecraftforge.common.MinecraftForge;

/**
 * @author keelfy
 */
public enum KServerEvents {
	INSTANCE;

	public static void register() {
		MinecraftForge.EVENT_BUS.register(INSTANCE);
	}
}
