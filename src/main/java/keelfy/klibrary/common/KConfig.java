package keelfy.klibrary.common;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

/**
 * @author keelfy
 */
public class KConfig {

	private Configuration config;

	public static boolean debug = false;

	public void load(File suggesstedFile) {
		this.config = new Configuration(suggesstedFile);
		this.reload();
	}

	public void reload() {
		this.config.load();
		debug = this.config.getBoolean("debug", config.CATEGORY_GENERAL, false, "Enables debug logging");
		this.config.save();
	}
}
