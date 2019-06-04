package keelfy.klibrary.server.commands.exceptions;

import java.util.*;

import javax.annotation.Nullable;

/**
 * @author sk89q
 * @author keelfy
 */
public class KCommandException extends Exception {

	private List<String> commandStack = new ArrayList<String>();

	public KCommandException() {
		super();
	}

	public KCommandException(String message) {
		super(message);
	}

	public KCommandException(String message, Throwable t) {
		super(message, t);
	}

	public KCommandException(Throwable t) {
		super(t);
	}

	public void prependStack(String name) {
		commandStack.add(name);
	}

	/**
	 * Gets the command that was called, which will include the sub-command (i.e.
	 * "/br sphere").
	 *
	 * @param prefix       the command shebang character (such as "/") -- may be
	 *                     empty
	 * @param spacedSuffix a suffix to put at the end (optional) -- may be null
	 * @return the command that was used
	 */
	public String getCommandUsed(String prefix, @Nullable String spacedSuffix) {
		StringBuilder builder = new StringBuilder();
		if (prefix != null) {
			builder.append(prefix);
		}
		ListIterator<String> li = commandStack.listIterator(commandStack.size());
		while (li.hasPrevious()) {
			if (li.previousIndex() != commandStack.size() - 1) {
				builder.append(" ");
			}
			builder.append(li.previous());
		}
		if (spacedSuffix != null) {
			if (builder.length() > 0) {
				builder.append(" ");
			}
			builder.append(spacedSuffix);
		}
		return builder.toString().trim();
	}

}