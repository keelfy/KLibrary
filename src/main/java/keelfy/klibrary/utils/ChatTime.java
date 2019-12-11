package keelfy.klibrary.utils;

/**
 * @author keelfy
 */
public class ChatTime {

	private static final String SPLITTER = "/";

	private long period;

	public ChatTime(long period) {
		this.period = period;
	}

	public ChatTime(int seconds, int minutes, int hours) {
		this.period = seconds * 1000 + minutes * 60 * 1000 + hours * 60 * 60 * 1000;
	}

	/**
	 * String formatting: 1s/1m/1h (for example may be only 1s or 1m/1h)
	 * 
	 * @param str Time data
	 * @return {@link ChatTime}.
	 */
	public static ChatTime parse(String str) {
		return parse(str, SPLITTER);
	}

	public static ChatTime parse(String str, String splitter) {
		long millis = 0;

		String[] splitted = str.split(splitter);

		if (splitted.length == 0) {
			splitted = new String[] { str };
		}

		for (String s : splitted) {
			for (TimeType type : TimeType.values()) {
				if (type.toString().equalsIgnoreCase(s.substring(s.length() - 1, s.length()))) {
					millis += type.millis * Integer.parseInt(s.substring(0, s.length() - 1));
				}
			}
		}
		return new ChatTime(millis);
	}

	public long getPeriod() {
		return period;
	}

	private enum TimeType {
		S,
		M,
		H;

		private final int millis;

		private TimeType() {
			this.millis = (int) (Math.pow(60, ordinal()) * 1000);
		}
	}

}
