package org.chun.spring.custom.annotation.hook;

import java.lang.annotation.Annotation;

import org.springframework.beans.factory.FactoryBean;

public abstract class FactoryBeanHook<T> implements FactoryBean<T>{
	
	protected Class<T> innerClass;
	protected Class<? extends Annotation> customAnnotation;

	public void setInnerClass(Class<T> innerClass) {
		this.innerClass = innerClass;
	}
	public void setCustomAnnotatione(Class<? extends Annotation> customAnnotation) {
		this.customAnnotation = customAnnotation;
	}
	
}
