package org.chun.spring.custom.annotation.defaults.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;


@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Replace {
	
	@AliasFor("replaceMethodInvokerRef")
	String[] value();
	
	@AliasFor("value")
	String replaceMethodInvokerRef() default "";
	
}
