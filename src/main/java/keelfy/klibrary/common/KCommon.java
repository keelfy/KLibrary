package keelfy.klibrary.common;

import cpw.mods.fml.common.event.*;
import keelfy.klibrary.KLibrary;

/**
 * This is iternal class, do not use it.
 * 
 * @author keelfy
 */
public class KCommon {

	public void preInit(final FMLPreInitializationEvent event) {
		KLibrary.getConfig().load(event.getSuggestedConfigurationFile());
	}

	public void init(final FMLInitializationEvent event) {
		KCommonEvents.register();
	}

	public void serverStarting(final FMLServerStartingEvent event) {}

}
