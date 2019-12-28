package gloomyfolken.hooklib.asm;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
public @interface Hook {

	ReturnCondition returnCondition() default ReturnCondition.NEVER;

	HookPriority priority() default HookPriority.NORMAL;

	String targetMethod() default "";

	String returnType() default "";

	boolean createMethod() default false;

	boolean isMandatory() default false;

	boolean injectOnExit() default false;

	@Deprecated
	int injectOnLine() default -1;

	String returnAnotherMethod() default "";

	boolean returnNull() default false;

	boolean booleanReturnConstant() default false;

	byte byteReturnConstant() default 0;

	short shortReturnConstant() default 0;

	int intReturnConstant() default 0;

	long longReturnConstant() default 0L;

	float floatReturnConstant() default 0.0F;

	double doubleReturnConstant() default 0.0D;

	char charReturnConstant() default 0;

	String stringReturnConstant() default "";

	@Target(ElementType.PARAMETER)
	@interface LocalVariable {
		int value();
	}

	@Target(ElementType.PARAMETER)
	@interface ReturnValue {}
}
