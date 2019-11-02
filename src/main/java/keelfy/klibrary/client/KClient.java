package keelfy.klibrary.client;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.*;
import keelfy.klibrary.common.KCommon;

/**
 * @author keelfy
 */
@SideOnly(Side.CLIENT)
public final class KClient extends KCommon {

	@Override
	public void preInit(final FMLPreInitializationEvent event) {
		super.preInit(event);

		KClientPacketHandler.register();
	}
}
