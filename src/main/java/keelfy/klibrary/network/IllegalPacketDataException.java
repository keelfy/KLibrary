package keelfy.klibrary.network;

import io.netty.buffer.ByteBuf;

/**
 * Throws when sent data type can not be written to {@link ByteBuf}.
 * 
 * @author keelfy
 */
public class IllegalPacketDataException extends Exception {

	public IllegalPacketDataException(Class<?> clazz) {
		super("Class " + clazz.getCanonicalName() + " can not be sent.");
	}
}
