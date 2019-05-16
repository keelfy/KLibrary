package keelfy.klibrary.common;

import cpw.mods.fml.common.event.*;
import keelfy.klibrary.KLibrary;
import keelfy.klibrary.network.KNetwork;

/**
 * This is iternal class, do not use it.
 * 
 * @author keelfy
 */
public class KCommon {

	public void preInit(final FMLPreInitializationEvent event) {
		KLibrary.network = new KNetwork(KLibrary.MOD_ID);
	}

	public void serverStarting(final FMLServerStartingEvent event) {}

}
