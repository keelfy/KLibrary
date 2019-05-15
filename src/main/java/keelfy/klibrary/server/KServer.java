package keelfy.klibrary.server;

import cpw.mods.fml.common.event.*;
import keelfy.klibrary.common.KCommon;

/**
 * @author keelfy
 */
public class KServer extends KCommon {

	@Override
	public void preInit(final FMLPreInitializationEvent event) {
		super.preInit(event);
	}

	@Override
	public void serverStarting(final FMLServerStartingEvent event) {
		super.serverStarting(event);
		KTestCommands.register(event.getServer());
	}
}
