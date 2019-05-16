package keelfy.klibrary.network;

import cpw.mods.fml.common.network.*;
import cpw.mods.fml.common.network.FMLNetworkEvent.*;
import cpw.mods.fml.relauncher.*;
import io.netty.buffer.ByteBuf;
import keelfy.klibrary.client.KClientPacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetHandlerPlayServer;

/**
 * This class receives data from {@link ByteBuf} sent from {@link KNetwork}.
 * 
 * @see {@link KNetwork} methods to find out sending proccess.
 * 
 * @author keelfy
 */
public abstract class KPacketReceiver {

	protected ByteBuf buffer;
	protected boolean typesMarked;
	protected int markedTypesLength;
	protected int packetNumber;
	protected Object[] receivedMarkedTypes;

	/**
	 * Initializes basic variables of receiver.
	 * 
	 * @see {@link KClientPacketHandler} for example.
	 * 
	 * @param buffer received {@link ByteBuf}.
	 */
	public KPacketReceiver(ByteBuf buffer) {
		this.buffer = buffer;
		this.typesMarked = buffer.readBoolean();
		this.markedTypesLength = isTypesMarked() ? buffer.readInt() : 0;
		this.packetNumber = buffer.readInt();
		this.receivedMarkedTypes = new Object[markedTypesLength];

		if (isTypesMarked()) {
			for (int i = 0; i < markedTypesLength; i++) {
				EnumDataType dataType = EnumDataType.values()[buffer.readByte()];

				switch (dataType) {
				case BOOLEAN:
					this.receivedMarkedTypes[i] = buffer.readBoolean();
					break;
				case DOUBLE:
					this.receivedMarkedTypes[i] = buffer.readDouble();
					break;
				case FLOAT:
					this.receivedMarkedTypes[i] = buffer.readFloat();
					break;
				case INTEGER:
					this.receivedMarkedTypes[i] = buffer.readInt();
					break;
				case LONG:
					this.receivedMarkedTypes[i] = buffer.readLong();
					break;
				case STRING:
					this.receivedMarkedTypes[i] = ByteBufUtils.readUTF8String(buffer);
					break;
				case ITEMSTACK:
					this.receivedMarkedTypes[i] = ByteBufUtils.readItemStack(buffer);
					break;
				case TAG:
					this.receivedMarkedTypes[i] = ByteBufUtils.readTag(buffer);
					break;
				}
			}
		}
	}

	/**
	 * Gets received {@link ByteBuf}.
	 * 
	 * @return buffer
	 */
	public ByteBuf getBuffer() {
		return buffer;
	}

	/**
	 * Gets number of sent packet.
	 * 
	 * @return packet number
	 */
	public int getPacketNumber() {
		return packetNumber;
	}

	/**
	 * Checks if variables types were marked
	 * 
	 * @return state
	 */
	public boolean isTypesMarked() {
		return typesMarked;
	}

	/**
	 * Gets all received <b>marked</b> data types.
	 * 
	 * @return array of received types.
	 */
	public Object[] getReceivedMarkedTypes() {
		return receivedMarkedTypes;
	}

	/**
	 * Gets amount of sent <b>marked</b> data types.
	 * 
	 * @return amount
	 */
	public int getMarkedTypesLength() {
		return markedTypesLength;
	}

	/**
	 * Creates an instance of receiver by {@link ClientCustomPacketEvent}.
	 * 
	 * @see {@link KClientPacketHandler} for example.
	 */
	@SideOnly(Side.CLIENT)
	public static class ClientPacketReceiver extends KPacketReceiver {

		private Minecraft mc;
		private EntityClientPlayerMP player;

		/**
		 * Creates an instance of receiver by {@link ClientCustomPacketEvent}.
		 * 
		 * @param event fired event with data
		 */
		public ClientPacketReceiver(final FMLNetworkEvent.ClientCustomPacketEvent event) {
			super(event.packet.payload());

			this.mc = Minecraft.getMinecraft();
			this.player = mc.thePlayer;
		}

		/**
		 * Gets client-sided player
		 * 
		 * @return player instance.
		 */
		public EntityClientPlayerMP getPlayer() {
			return player;
		}

		/**
		 * Gets {@link Minecraft} instance.
		 * 
		 * @return {@link Minecraft} instance.
		 */
		public Minecraft getMC() {
			return mc;
		}

	}

	/**
	 * Creates an instance of receiver by {@link ServerCustomPacketEvent}.
	 */
	public static class ServerPacketReceiver extends KPacketReceiver {

		private EntityPlayerMP player;

		/**
		 * Creates an instance of receiver by {@link ServerCustomPacketEvent}.
		 */
		public ServerPacketReceiver(final FMLNetworkEvent.ServerCustomPacketEvent event) {
			super(event.packet.payload());

			this.player = ((NetHandlerPlayServer) event.handler).playerEntity;
		}

		/**
		 * Gets server-sided player
		 * 
		 * @return player instance.
		 */
		public EntityPlayerMP getPlayer() {
			return player;
		}
	}
}
