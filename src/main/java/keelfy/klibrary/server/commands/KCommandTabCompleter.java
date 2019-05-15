package keelfy.klibrary.server.commands;

import java.util.*;

import net.minecraft.server.MinecraftServer;

/**
 * @author keelfy
 * @date 13 мая 2019 г.
 */
public class KCommandTabCompleter {

	private List<String[]> variants;

	public KCommandTabCompleter() {
		this.variants = new ArrayList<String[]>();
	}

	public KCommandTabCompleter(Object[] objects) {
		this();
		this.create(objects);
	}

	public void create(Object[] objects) {
		for (Object object : objects) {
			String[] result = new String[] {};
			if (object instanceof String) {
				result = new String[] { (String) object };
			} else if (object instanceof String[]) {
				result = (String[]) object;
			} else if (object != null) {
				result = new String[] { object.toString() };
			} else {
				result = new String[] { "" };
			}

			variants.add(result);
		}
	}

	public List<String[]> getCompletions() {
		return variants;
	}

	public String[] getCompletion(int index) {
		if (index >= variants.size())
			return null;

		String[] result = this.variants.get(index);
		if (result.length == 1) {
			switch (result[0]) {
			case "<player>":
				return MinecraftServer.getServer().getAllUsernames();
			}
		}

		return result;
	}
}
