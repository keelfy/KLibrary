package keelfy.klibrary.server.commands;

import java.util.*;

import org.apache.commons.lang3.ArrayUtils;

import com.google.common.collect.Lists;

/**
 * @author keelfy
 */
public class KCommandInfo {

	private String name;
	private String usage;
	private List<String> aliases;
	private KCommandTabCompleter tabCompleter;
	private boolean canUseFromConsole;
	private boolean call;

	public KCommandInfo(String[] aliases, String usage) {
		if (aliases.length == 0)
			throw new IllegalArgumentException("Number of command aliases must be greater than 0");

		this.name = aliases[0];
		this.aliases = Arrays.asList(ArrayUtils.remove(aliases, 0));
		this.usage = usage;
		this.canUseFromConsole = true;
		this.tabCompleter = new KCommandTabCompleter();
		this.call = true;
	}

	public KCommandInfo(String name, String usage) {
		this.aliases = Lists.newArrayList();
		this.name = name;
		this.usage = usage;
		this.canUseFromConsole = true;
		this.tabCompleter = new KCommandTabCompleter();
		this.call = true;
	}

	public boolean isCall() {
		return call;
	}

	public void setCall(boolean call) {
		this.call = call;
	}

	public KCommandInfo createTabCompleter(Object[] objects) {
		this.tabCompleter.create(objects);
		return this;
	}

	public KCommandInfo disallowConsoleUsing() {
		this.canUseFromConsole = false;
		return this;
	}

	public boolean isUsableFromConsole() {
		return canUseFromConsole;
	}

	public KCommandTabCompleter getTabCompleter() {
		return tabCompleter;
	}

	public String getName() {
		return name;
	}

	public List<String> getAliases() {
		return aliases;
	}

	public String getUsage() {
		return usage;
	}
}
