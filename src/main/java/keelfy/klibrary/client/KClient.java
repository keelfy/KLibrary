package keelfy.klibrary.client;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import keelfy.klibrary.KLibrary;
import keelfy.klibrary.common.KCommon;

/**
 * This is iternal class, do not use it.
 * 
 * @author keelfy
 */
public class KClient extends KCommon {

	@Override
	public void preInit(final FMLPreInitializationEvent event) {
		super.preInit(event);

		KLibrary.network.registerPacketHandler(KClientPacketHandler.INSTANCE);
	}
}
