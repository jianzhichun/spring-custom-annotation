package org.chun.spring.custom.annotation.hook;

import java.lang.annotation.Annotation;

import org.springframework.beans.MutablePropertyValues;

public interface BeanPropertyValuesInitHook {
	
	void propertyValuesInit(MutablePropertyValues propertyValues, Class<?> targetClass, Class<? extends Annotation> declarationAnnotation);

}
