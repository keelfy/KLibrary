package keelfy.klibrary.server.commands;

import java.lang.annotation.*;

/**
 * @author keelfy
 * @date 26 мая 2019 г.
 */
@Target(value = ElementType.METHOD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface KChildCommands {

	Class[] value();

}
