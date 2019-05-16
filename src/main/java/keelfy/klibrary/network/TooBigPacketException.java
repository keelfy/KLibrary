package keelfy.klibrary.network;

/**
 * Throws when sent packet is larger than {@link Short#MAX_VALUE}.
 * 
 * @author keelfy
 */
public final class TooBigPacketException extends Exception {

	public TooBigPacketException(int packetNumber) {
		super("Packet " + packetNumber + " too big to send! Try to split packet's data.");
	}
}
