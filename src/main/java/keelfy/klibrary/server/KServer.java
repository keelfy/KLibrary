package keelfy.klibrary.server;

import cpw.mods.fml.common.event.*;
import keelfy.klibrary.common.KCommon;
import keelfy.klibrary.server.commands.KCommandManager;

/**
 * @author keelfy
 */
public class KServer extends KCommon {

	private static KCommandManager commandManager = new KCommandManager();

	@Override
	public void preInit(final FMLPreInitializationEvent event) {
		super.preInit(event);

//		KServerEvents.INSTANCE.register();
	}

	@Override
	public void serverStarting(final FMLServerStartingEvent event) {
		super.serverStarting(event);
//		KTestCommands.register(event.getServer());
	}

	public static KCommandManager getCommandManager() {
		return commandManager;
	}
}
