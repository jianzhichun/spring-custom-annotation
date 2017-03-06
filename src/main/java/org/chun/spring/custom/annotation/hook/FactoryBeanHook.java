package org.chun.spring.custom.annotation.hook;

import java.lang.annotation.Annotation;

import org.springframework.beans.factory.FactoryBean;

public abstract class FactoryBeanHook<T> implements FactoryBean<T>{
	
	protected String innerClassName;
	protected Class<? extends Annotation> customAnnotation;

	public void setInnerClassName(String innerClassName) {
		this.innerClassName = innerClassName;
	}
	public void setCustomAnnotation(Class<? extends Annotation> customAnnotation) {
		this.customAnnotation = customAnnotation;
	}
	
}
