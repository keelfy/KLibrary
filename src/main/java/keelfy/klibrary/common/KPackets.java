package keelfy.klibrary.common;

import cpw.mods.fml.relauncher.*;
import io.netty.buffer.ByteBuf;
import keelfy.klibrary.network.KPacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.player.EntityPlayerMP;

/**
 * @author keelfy
 */
public class KPackets extends KPacketHandler {

	@Override
	@SideOnly(Side.CLIENT)
	public void onClientPacket(Minecraft mc, EntityClientPlayerMP player, ByteBuf buffer, int packetNumber) {

	}

	@Override
	public void onServerPacket(EntityPlayerMP player, ByteBuf buffer, int packetNumber) {}

}
