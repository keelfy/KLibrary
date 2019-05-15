package keelfy.klibrary;

import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.*;
import cpw.mods.fml.common.event.*;
import keelfy.klibrary.common.KCommon;
import keelfy.klibrary.network.KNetwork;

/**
 * @author keelfy
 */
@Mod(modid = KLibrary.MOD_ID, name = KLibrary.MOD_NAME, version = KLibrary.MOD_VERSION)
public final class KLibrary {

	@SidedProxy(clientSide = "keelfy.klibrary.client.KClient", serverSide = "keelfy.klibrary.server.KServer")
	public static KCommon proxy;

	public static final String MOD_ID = "klibrary";
	public static final String MOD_NAME = "KLibrary";
	public static final String MOD_VERSION = "@VERSIOM@";

	public static Logger logger;

	public static KNetwork network;

	@Mod.EventHandler
	public void preInit(final FMLPreInitializationEvent event) {
		logger = event.getModLog();
		proxy.preInit(event);
	}

	@Mod.EventHandler
	public void serverStarting(final FMLServerStartingEvent event) {
		proxy.serverStarting(event);
	}
}
