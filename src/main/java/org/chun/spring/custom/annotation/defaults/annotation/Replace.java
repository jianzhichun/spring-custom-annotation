package org.chun.spring.custom.annotation.defaults.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;



@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Replace {
	
	Class<? extends ReplaceMethodInvoker> value() default ReplaceMethodInvoker.class;
	
}
