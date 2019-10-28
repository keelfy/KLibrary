package gloomyfolken.hooklib.asm;

import java.util.logging.*;

import keelfy.klibrary.KLibrary;

public interface HookLogger {

	void debug(String message);

	void warning(String message);

	void severe(String message);

	void severe(String message, Throwable cause);

	class SystemOutLogger implements HookLogger {

		@Override
		public void debug(String message) {
			if (KLibrary.getConfig().debug)
				System.out.println("[DEBUG] " + message);
		}

		@Override
		public void warning(String message) {
			if (KLibrary.getConfig().debug)
				System.out.println("[WARNING] " + message);
		}

		@Override
		public void severe(String message) {
			if (KLibrary.getConfig().debug)
				System.out.println("[SEVERE] " + message);
		}

		@Override
		public void severe(String message, Throwable cause) {
			if (KLibrary.getConfig().debug) {
				severe(message);
				cause.printStackTrace();
			}
		}
	}

	class VanillaLogger implements HookLogger {

		private Logger logger;

		public VanillaLogger(Logger logger) {
			this.logger = logger;
		}

		@Override
		public void debug(String message) {
			if (KLibrary.getConfig().debug)
				logger.fine(message);
		}

		@Override
		public void warning(String message) {
			if (KLibrary.getConfig().debug)
				logger.warning(message);
		}

		@Override
		public void severe(String message) {
			if (KLibrary.getConfig().debug)
				logger.severe(message);
		}

		@Override
		public void severe(String message, Throwable cause) {
			if (KLibrary.getConfig().debug)
				logger.log(Level.SEVERE, message, cause);
		}
	}

}
