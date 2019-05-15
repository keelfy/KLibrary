package keelfy.klibrary.common;

import cpw.mods.fml.common.event.*;
import keelfy.klibrary.KLibrary;
import keelfy.klibrary.network.KNetwork;

/**
 * @author keelfy
 */
public class KCommon {

	public void preInit(final FMLPreInitializationEvent event) {
		KLibrary.network = new KNetwork(KLibrary.MOD_ID).registerPacketHandler(new KPackets());
	}

	public void serverStarting(final FMLServerStartingEvent event) {}

}
