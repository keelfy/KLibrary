package keelfy.klibrary.network;

import cpw.mods.fml.common.network.*;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import cpw.mods.fml.relauncher.*;
import io.netty.buffer.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Little framework developed to simplify work with {@link FMLEventChannel}
 * 
 * @author keelfy
 */
public final class KNetwork {

	private final String channelName;
	private final FMLEventChannel channel;

	/**
	 * Creates ne instanceof of FMLEventChannel by specific channel name
	 * 
	 * @param channelName name of channel
	 */
	public KNetwork(final String channelName) {
		this.channelName = channelName;
		this.channel = NetworkRegistry.INSTANCE.newEventDrivenChannel(channelName);
	}

	/**
	 * Registers packet handler of this channel
	 * 
	 * @param handler instance of handler
	 */
	public KNetwork registerPacketHandler(KPacketHandler handler) {
		channel.register(handler);
		return this;
	}

	/**
	 * Sends packet to a specific player by enumeration of packets
	 * 
	 * @param player     player
	 * @param packetEnum the enumeration that stores a list of your packets
	 * @param obs        dispatch objects
	 */
	public void sendTo(EntityPlayerMP player, Enum<?> packetEnum, Object... obs) {
		this.sendTo(player, packetEnum.ordinal(), obs);
	}

	/**
	 * Sends packet to a specific player by number of packet
	 * 
	 * @param player       player
	 * @param packetNumber the unique (in the field of your mod) number of packet
	 * @param obs          dispatch objects
	 */
	public void sendTo(EntityPlayerMP player, int packetNumber, Object... obs) {
		ByteBuf buffer = Unpooled.buffer();

		if (!fillBuffer(buffer, packetNumber, obs))
			return;

		channel.sendTo(new FMLProxyPacket(buffer, channelName), player);
	}

	/**
	 * Sends packet to all entities around specific entity by enumeration of packets
	 * 
	 * @param entity     central entity
	 * @param packetEnum the enumeration that stores a list of your packets
	 * @param obs        dispatch objects
	 */
	public void sendToAllAround(Entity entity, Enum<?> packetEnum, Object... obs) {
		this.sendToAllAround(entity, packetEnum.ordinal(), obs);
	}

	/**
	 * Sends packet to all entities around specific entity by number of packet
	 * 
	 * @param entity       central entity
	 * @param packetNumber the unique (in the field of your mod) number of packet
	 * @param obs          dispatch objects
	 */
	public void sendToAllAround(Entity entity, int packetNumber, Object... obs) {
		ByteBuf buffer = Unpooled.buffer();

		if (!fillBuffer(buffer, packetNumber, obs))
			return;

		TargetPoint point = new TargetPoint(entity.dimension, entity.posX, entity.posY, entity.posZ, 60);
		channel.sendToAllAround(new FMLProxyPacket(buffer, channelName), point);
	}

	/**
	 * Sends packet to all entities by enumeration of packets
	 * 
	 * @param packetEnum the enumeration that stores a list of your packets
	 * @param obs        dispatch objects
	 */
	public void sendToAll(Enum<?> packetEnum, Object... obs) {
		this.sendToAll(packetEnum.ordinal(), obs);
	}

	/**
	 * Sends packet to all entities by number of packet
	 * 
	 * @param packetNumber the unique (in the field of your mod) number of packet
	 * @param obs          dispatch objects
	 */
	public void sendToAll(int packetNumber, Object... obs) {
		ByteBuf buffer = Unpooled.buffer();

		if (!fillBuffer(buffer, packetNumber, obs))
			return;

		channel.sendToAll(new FMLProxyPacket(buffer, channelName));
	}

	/**
	 * Sends packet to server from client by enumeration of packets
	 * 
	 * @param packetEnum the enumeration that stores a list of your packets
	 * @param obs        dispatch objects
	 */
	@SideOnly(Side.CLIENT)
	public void sendToServer(Enum<?> packetEnum, Object... obs) {
		this.sendToServer(packetEnum.ordinal(), obs);
	}

	/**
	 * Sends packet to server from client by number of packet
	 * 
	 * @param packetNumber the unique (in the field of your mod) number of packet
	 * @param obs          dispatch objects
	 */
	@SideOnly(Side.CLIENT)
	public void sendToServer(int packetNumber, Object... obs) {
		ByteBuf buffer = Unpooled.buffer();

		if (!fillBuffer(buffer, packetNumber, obs))
			return;

		channel.sendToServer(new FMLProxyPacket(buffer, channelName));
	}

	/**
	 * Fills the {@link ByteBuf} by number of packet and dispatch objects Type of
	 * 
	 * dispatch object might be: Enum (number will be sent), int, boolean, String,
	 * float, long, double, NBTTagCompound, ItemStack
	 * 
	 * @param buffer       {@link ByteBuf} instance to fill
	 * @param packetNumber the unique (in the field of your mod) number of packet
	 * @param obs          dispatch objects
	 * @return result of filling
	 */
	public static boolean fillBuffer(ByteBuf buffer, int packetNumber, Object... obs) {
		try {
			buffer.writeInt(packetNumber);

			for (Object ob : obs) {
				if (ob == null)
					continue;

				if (ob instanceof Enum) {
					buffer.writeInt(((Enum<?>) ob).ordinal());
				} else if (ob instanceof Integer) {
					buffer.writeInt((Integer) ob);
				} else if (ob instanceof Boolean) {
					buffer.writeBoolean((Boolean) ob);
				} else if (ob instanceof String) {
					ByteBufUtils.writeUTF8String(buffer, (String) ob);
				} else if (ob instanceof Float) {
					buffer.writeFloat((Float) ob);
				} else if (ob instanceof Long) {
					buffer.writeLong((Long) ob);
				} else if (ob instanceof Double) {
					buffer.writeDouble((Double) ob);
				} else if (ob instanceof NBTTagCompound) {
					ByteBufUtils.writeTag(buffer, (NBTTagCompound) ob);
				} else if (ob instanceof ItemStack) {
					ByteBufUtils.writeItemStack(buffer, (ItemStack) ob);
				}
			}

			if (buffer.array().length >= Short.MAX_VALUE)
				throw new TooBigPacketException("Packet " + packetNumber + " too big to send! Try to split packet's data.");
		} catch (TooBigPacketException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
