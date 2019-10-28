package keelfy.klibrary.common;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

/**
 * @author keelfy
 */
public class KConfig {

	private Configuration config;

	public static boolean debug;

	public void load(File suggesstedFile) {
		this.config = new Configuration(suggesstedFile);
		this.reload();
	}

	public void reload() {
		this.config.load();

		debug = this.config.getBoolean("debug_mode", config.CATEGORY_GENERAL, false, "Enables debug logging");

		this.config.save();
	}
}
