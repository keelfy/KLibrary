package keelfy.klibrary.common;

import cpw.mods.fml.common.event.*;

/**
 * This is iternal class, do not use it.
 * 
 * @author keelfy
 */
public class KCommon {

	public void preInit(final FMLPreInitializationEvent event) {
		KCommonEvents.register();
	}

	public void serverStarting(final FMLServerStartingEvent event) {}

}
