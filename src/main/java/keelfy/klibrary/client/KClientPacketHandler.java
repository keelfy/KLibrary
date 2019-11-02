package keelfy.klibrary.client;

import org.apache.commons.lang3.ArrayUtils;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent;
import cpw.mods.fml.relauncher.*;
import keelfy.klibrary.KLibrary;
import keelfy.klibrary.network.KPacketReceiver.ClientPacketReceiver;

/**
 * @author keelfy
 */
@SideOnly(Side.CLIENT)
public enum KClientPacketHandler {
	INSTANCE;

	public static void register() {
		KLibrary.getNetwork().registerPacketHandler(INSTANCE);
	}

	@SubscribeEvent
	public void onClientPacket(final FMLNetworkEvent.ClientCustomPacketEvent event) {
		final ClientPacketReceiver receiver = new ClientPacketReceiver(event);
		final KClientPackets packet = KClientPackets.values()[receiver.getPacketNumber()];

		if (receiver.getPlayer() == null)
			return;

		switch (packet) {
		case MESSAGE:
			Object[] received = receiver.getReceivedMarkedTypes();

			if (receiver.getMarkedTypesLength() == 0)
				break;

			String code = (String) received[0];
			KLocal.sendLocalizedMessage(receiver.getPlayer(), code, ArrayUtils.remove(received, 0));
			break;
		}
	}

}
