package keelfy.klibrary.server.commands.exceptions;

import javax.annotation.Nullable;

/**
 * @author keelfy
 * @date 5 июн. 2019 г.
 */
public class KCommandUsageException extends KCommandException {

	protected @Nullable String usage;

	public KCommandUsageException(String message) {
		this(message, null);
	}

	public KCommandUsageException(String message, @Nullable String usage) {
		super(message);
		this.usage = usage;
	}

	public String getUsage() {
		return usage != null ? usage : "";
	}

	public void offerUsage(String usage) {
		if (this.usage == null) {
			this.usage = usage;
		}
	}
}