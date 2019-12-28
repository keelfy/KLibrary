package keelfy.klibrary;

import org.apache.logging.log4j.*;

import cpw.mods.fml.common.*;
import cpw.mods.fml.common.event.*;
import keelfy.klibrary.common.*;
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

	private static Logger logger = LogManager.getLogger(KLibrary.class.getSimpleName());
	private static KNetwork network;
	private static KConfig config = new KConfig();

	@Mod.EventHandler
	public void preInit(final FMLPreInitializationEvent event) {
		network = new KNetwork(MOD_ID);
		proxy.preInit(event);
	}

	@Mod.EventHandler
	public void init(final FMLInitializationEvent event) {
		proxy.init(event);
	}

	@Mod.EventHandler
	public void serverStarting(final FMLServerStartingEvent event) {
		proxy.serverStarting(event);
	}

	public static Logger getLogger() {
		return logger;
	}

	public static KNetwork getNetwork() {
		return network;
	}

	public static KConfig getConfig() {
		return config;
	}
}
