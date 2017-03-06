package org.chun.spring.custom.annotation;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.chun.spring.custom.annotation.defaults.annotation.MethodReplace;
import org.chun.spring.custom.annotation.defaults.annotation.Replace;
import org.chun.spring.custom.annotation.defaults.annotation.hook.MethodReplaceBeanPropertyValuesInitHook;
import org.chun.spring.custom.annotation.defaults.annotation.hook.MethodReplaceFactoryBeanHook;
import org.chun.spring.custom.annotation.hook.BeanDefinitionPostProcessor;
import org.chun.spring.custom.annotation.hook.BeanPropertyValuesInitHook;
import org.chun.spring.custom.annotation.hook.FactoryBeanHook;
import org.chun.spring.custom.annotation.registrar.CustomAnnotationScannerRegistrar;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(CustomAnnotationScannerRegistrar.class)
public @interface CustomAnnotationScan {

	@AliasFor("basePackages")
	String[] value() default {};
	
	@AliasFor("value")
	String[] basePackages() default {};
	
	Class<? extends Annotation> declarationAnnotation() default MethodReplace.class;
	
	Class<? extends Annotation> customAnnotation() default Replace.class;
	
	Class<? extends BeanDefinitionPostProcessor> beanDefinitionPostProcessor() default BeanDefinitionPostProcessor.class;
	  
	Class<? extends BeanPropertyValuesInitHook> propertyValuesInitHook() default MethodReplaceBeanPropertyValuesInitHook.class;
	
	@SuppressWarnings("rawtypes")
	Class<? extends FactoryBeanHook> factoryBeanHook() default MethodReplaceFactoryBeanHook.class;
}
