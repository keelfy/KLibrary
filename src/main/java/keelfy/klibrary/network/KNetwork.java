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

	public FMLEventChannel getChannel() {
		return channel;
	}

	public String getChannelName() {
		return channelName;
	}

	/**
	 * Registers packet handler of this channel
	 * 
	 * @param handler instance of handler
	 */
	public KNetwork registerPacketHandler(Object handler) {
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
		this.sendTo(false, player, packetEnum, obs);
	}

	/**
	 * Sends packet to a specific player by enumeration of packets
	 * 
	 * @param markTypes  use this if don't know dispatch objects types in advance
	 * @param player     player
	 * @param packetEnum the enumeration that stores a list of your packets
	 * @param obs        dispatch objects
	 */
	public void sendTo(boolean markTypes, EntityPlayerMP player, Enum<?> packetEnum, Object... obs) {
		this.sendTo(markTypes, player, packetEnum.ordinal(), obs);
	}

	/**
	 * Sends packet to a specific player by number of packet
	 * 
	 * @param player       player
	 * @param packetNumber the unique (in the field of your mod) number of packet
	 * @param obs          dispatch objects
	 */
	public void sendTo(EntityPlayerMP player, int packetNumber, Object... obs) {
		this.sendTo(false, player, packetNumber, obs);
	}

	/**
	 * Sends packet to a specific player by number of packet
	 * 
	 * @param markTypes    use this if don't know dispatch objects types in advance
	 * @param player       player
	 * @param packetNumber the unique (in the field of your mod) number of packet
	 * @param obs          dispatch objects
	 */
	public void sendTo(boolean markTypes, EntityPlayerMP player, int packetNumber, Object... obs) {
		ByteBuf buffer = Unpooled.buffer();

		if (!fillBuffer(buffer, markTypes, packetNumber, obs))
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
		this.sendToAllAround(false, entity, packetEnum, obs);
	}

	/**
	 * Sends packet to all entities around specific entity by enumeration of packets
	 * 
	 * @param markTypes  use this if don't know dispatch objects types in advance
	 * @param entity     central entity
	 * @param packetEnum the enumeration that stores a list of your packets
	 * @param obs        dispatch objects
	 */
	public void sendToAllAround(boolean markTypes, Entity entity, Enum<?> packetEnum, Object... obs) {
		this.sendToAllAround(markTypes, entity, packetEnum.ordinal(), obs);
	}

	/**
	 * Sends packet to all entities around specific entity by number of packet
	 * 
	 * @param entity       central entity
	 * @param packetNumber the unique (in the field of your mod) number of packet
	 * @param obs          dispatch objects
	 */
	public void sendToAllAround(Entity entity, int packetNumber, Object... obs) {
		this.sendToAllAround(false, entity, packetNumber, obs);
	}

	/**
	 * Sends packet to all entities around specific entity by number of packet
	 * 
	 * @param markTypes    use this if don't know dispatch objects types in advance
	 * @param entity       central entity
	 * @param packetNumber the unique (in the field of your mod) number of packet
	 * @param obs          dispatch objects
	 */
	public void sendToAllAround(boolean markTypes, Entity entity, int packetNumber, Object... obs) {
		this.sendToAllAround(markTypes, entity, packetNumber, 60, obs);
	}

	/**
	 * Sends packet to all entities around specific entity by number of packet
	 * 
	 * @param markTypes    use this if don't know dispatch objects types in advance
	 * @param entity       central entity
	 * @param packetNumber the unique (in the field of your mod) number of packet
	 * @param range        range
	 * @param obs          dispatch objects
	 */
	public void sendToAllAround(boolean markTypes, Entity entity, int packetNumber, int range, Object... obs) {
		ByteBuf buffer = Unpooled.buffer();

		if (!fillBuffer(buffer, markTypes, packetNumber, obs))
			return;

		TargetPoint point = new TargetPoint(entity.dimension, entity.posX, entity.posY, entity.posZ, range);
		channel.sendToAllAround(new FMLProxyPacket(buffer, channelName), point);
	}

	/**
	 * Sends packet to all entities by enumeration of packets
	 * 
	 * @param packetEnum the enumeration that stores a list of your packets
	 * @param obs        dispatch objects
	 */
	public void sendToAll(Enum<?> packetEnum, Object... obs) {
		this.sendToAll(false, packetEnum, obs);
	}

	/**
	 * 
	 * Sends packet to all entities by enumeration of packets
	 * 
	 * @param markTypes  use this if don't know dispatch objects types in advance
	 * @param packetEnum the enumeration that stores a list of your packets
	 * @param obs        dispatch objects
	 */
	public void sendToAll(boolean markTypes, Enum<?> packetEnum, Object... obs) {
		this.sendToAll(markTypes, packetEnum.ordinal(), obs);
	}

	/**
	 * Sends packet to all entities by number of packet
	 * 
	 * @param packetNumber the unique (in the field of your mod) number of packet
	 * @param obs          dispatch objects
	 */
	public void sendToAll(int packetNumber, Object... obs) {
		this.sendToAll(false, packetNumber, obs);
	}

	/**
	 * Sends packet to all entities by number of packet
	 * 
	 * @param markTypes    use this if don't know dispatch objects types in advance
	 * @param packetNumber the unique (in the field of your mod) number of packet
	 * @param obs          dispatch objects
	 */
	public void sendToAll(boolean markTypes, int packetNumber, Object... obs) {
		ByteBuf buffer = Unpooled.buffer();

		if (!fillBuffer(buffer, markTypes, packetNumber, obs))
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
		this.sendToServer(false, packetEnum, obs);
	}

	/**
	 * Sends packet to server from client by enumeration of packets
	 * 
	 * @param markTypes  use this if don't know dispatch objects types in advance
	 * @param packetEnum the enumeration that stores a list of your packets
	 * @param obs        dispatch objects
	 */
	@SideOnly(Side.CLIENT)
	public void sendToServer(boolean markTypes, Enum<?> packetEnum, Object... obs) {
		this.sendToServer(markTypes, packetEnum.ordinal(), obs);
	}

	/**
	 * Sends packet to server from client by number of packet
	 * 
	 * @param packetNumber the unique (in the field of your mod) number of packet
	 * @param obs          dispatch objects
	 */
	@SideOnly(Side.CLIENT)
	public void sendToServer(int packetNumber, Object... obs) {
		this.sendToServer(false, packetNumber, obs);
	}

	/**
	 * Sends packet to server from client by number of packet
	 * 
	 * @param markTypes    use this if don't know dispatch objects types in advance
	 * @param packetNumber the unique (in the field of your mod) number of packet
	 * @param obs          dispatch objects
	 */
	@SideOnly(Side.CLIENT)
	public void sendToServer(boolean markTypes, int packetNumber, Object... obs) {
		ByteBuf buffer = Unpooled.buffer();

		if (!fillBuffer(buffer, markTypes, packetNumber, obs))
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
	 * @param markTypes    use this if don't know dispatch objects types in advance
	 * @param packetNumber the unique (in the field of your mod) number of packet
	 * @param objects      dispatch objects
	 * @return result of filling
	 */
	public static boolean fillBuffer(ByteBuf buffer, boolean markTypes, int packetNumber, Object... objects) {
		try {
			buffer.writeBoolean(markTypes);
			if (markTypes)
				buffer.writeInt(objects.length);
			buffer.writeInt(packetNumber);

			for (Object object : objects) {
				if (object == null)
					continue;

				boolean written = false;

				for (EnumDataType dataType : EnumDataType.values()) {
					if (object.getClass().isAssignableFrom(dataType.getDataClass())) {

						if (markTypes)
							buffer.writeByte(dataType.ordinal());

						switch (dataType) {
						case BOOLEAN:
							buffer.writeBoolean((Boolean) object);
							break;
						case DOUBLE:
							buffer.writeDouble((Double) object);
							break;
						case FLOAT:
							buffer.writeFloat((Float) object);
							break;
						case INTEGER:
							buffer.writeInt((Integer) object);
							break;
						case LONG:
							buffer.writeLong((Long) object);
							break;
						case SHORT:
							buffer.writeShort((Short) object);
							break;
						case BYTE:
							buffer.writeByte((Byte) object);
							break;
						case CHAR:
							buffer.writeChar((Character) object);
							break;
						case STRING:
							ByteBufUtils.writeUTF8String(buffer, (String) object);
							break;
						case ITEMSTACK:
							ByteBufUtils.writeItemStack(buffer, (ItemStack) object);
							break;
						case TAG:
							ByteBufUtils.writeTag(buffer, (NBTTagCompound) object);
							break;
						}

						written = true;
						break;
					}
				}

				if (!written)
					throw new IllegalPacketDataException(object.getClass());
			}

			if (buffer.array().length >= Short.MAX_VALUE)
				throw new TooBigPacketException(packetNumber);

		} catch (TooBigPacketException e) {
			e.printStackTrace();
			return false;
		} catch (IllegalPacketDataException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
