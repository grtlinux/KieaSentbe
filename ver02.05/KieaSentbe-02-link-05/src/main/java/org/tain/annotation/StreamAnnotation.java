package org.tain.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = {ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface StreamAnnotation {
	String value() default "name";
	int length() default 0;
	boolean usable() default true;
	@Deprecated
	boolean useNull() default false;       // 
	boolean useNullSpace() default false;  // in JSON
	/*
	String value() default "-";
	int number() default 20;
	String[] language() default {};
	
	public enum Quality{BAD, GOOD, VERYGOOD}
	Quality quality() default Quality.GOOD;
	*/
}
