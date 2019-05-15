package keelfy.klibrary.server.commands;

import java.lang.annotation.*;

/**
 * @author keelfy
 * @date 13 мая 2019 г.
 */
@Target(value = ElementType.METHOD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface KCommandCompletions {

	Completion[] value();

	@Target(ElementType.TYPE)
	@Retention(RetentionPolicy.RUNTIME)
	public static @interface Completion {
		int num();

		String[] variants();
	}

}
