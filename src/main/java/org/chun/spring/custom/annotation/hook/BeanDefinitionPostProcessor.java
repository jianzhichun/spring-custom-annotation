package org.chun.spring.custom.annotation.hook;


import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.core.annotation.AnnotationAttributes;

public interface BeanDefinitionPostProcessor {
	
	void postProcessBeanDefinition(BeanDefinitionHolder holder, AnnotationAttributes customAnnotationScanAttr);
	
}
