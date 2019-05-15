package keelfy.klibrary.network;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent;
import cpw.mods.fml.relauncher.*;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetHandlerPlayServer;

/**
 * @author keelfy
 */
public abstract class KPacketHandler {

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onClientPacket(final FMLNetworkEvent.ClientCustomPacketEvent event) {
		final Minecraft mc = Minecraft.getMinecraft();
		final ByteBuf buffer = event.packet.payload();
		this.onClientPacket(mc, mc.thePlayer, buffer, buffer.readInt());
	}

	@SubscribeEvent
	public void onServerPacket(final FMLNetworkEvent.ServerCustomPacketEvent event) {
		final EntityPlayerMP player = ((NetHandlerPlayServer) event.handler).playerEntity;
		final ByteBuf buffer = event.packet.payload();
		this.onServerPacket(player, buffer, buffer.readInt());
	}

	@SideOnly(Side.CLIENT)
	public abstract void onClientPacket(Minecraft mc, EntityClientPlayerMP player, ByteBuf buffer, int packetNumber);

	public abstract void onServerPacket(EntityPlayerMP player, ByteBuf buffer, int packetNumber);

}
