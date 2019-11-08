package keelfy.klibrary.common;

import net.minecraftforge.common.MinecraftForge;

/**
 * For testing measures.
 * 
 * @author keelfy
 */
public enum KCommonEvents {
	INSTANCE;

	public static void register() {
		MinecraftForge.EVENT_BUS.register(INSTANCE);
	}

}
