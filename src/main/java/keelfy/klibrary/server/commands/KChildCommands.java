package keelfy.klibrary.server.commands;

import java.lang.annotation.*;

/**
 * @author keelfy
 */
@Target(value = ElementType.METHOD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface KChildCommands {

	Class[] value();

}
