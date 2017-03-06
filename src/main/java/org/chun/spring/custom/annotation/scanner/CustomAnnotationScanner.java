package org.chun.spring.custom.annotation.scanner;

import java.lang.annotation.Annotation;
import java.util.Set;

import org.chun.spring.custom.annotation.hook.BeanDefinitionPostProcessor;
import org.chun.spring.custom.annotation.hook.BeanPropertyValuesInitHook;
import org.chun.spring.custom.annotation.hook.FactoryBeanHook;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.Assert;

@SuppressWarnings("rawtypes")
public class CustomAnnotationScanner extends ClassPathBeanDefinitionScanner {

	private AnnotationAttributes customAnnotationScanAttr;
	private Class<? extends Annotation> declarationAnnotation;
	private Class<? extends Annotation> customAnnotation;
	private Class<? extends BeanDefinitionPostProcessor> beanDefinitionPostProcessor;
	private Class<? extends BeanPropertyValuesInitHook> beanPropertyValuesInitHook;
	private Class<? extends FactoryBeanHook> factoryBeanHook;

	public void setCustomAnnotationScanAttr(AnnotationAttributes annoAttrs) {
		this.customAnnotationScanAttr = annoAttrs;
		Class<? extends Annotation> declarationAnnotation = annoAttrs.getClass("declarationAnnotation");
		setDeclarationAnnotation(declarationAnnotation);

		Class<? extends Annotation> customAnnotation = annoAttrs.getClass("customAnnotation");
		setCustomAnnotation(customAnnotation);

		Class<? extends BeanDefinitionPostProcessor> beanDefinitionPostProcessor = annoAttrs
				.getClass("beanDefinitionPostProcessor");
		if (!BeanDefinitionPostProcessor.class.equals(beanDefinitionPostProcessor))
			setBeanDefinitionPostProcessor(beanDefinitionPostProcessor);

		Class<? extends BeanPropertyValuesInitHook> beanPropertyValuesInitHook = annoAttrs
				.getClass("propertyValuesInitHook");
		setBeanPropertyValuesInitHook(beanPropertyValuesInitHook);

		Class<? extends FactoryBeanHook> factoryBeanHook = annoAttrs.getClass("factoryBeanHook");
		setFactoryBeanHook(factoryBeanHook);
	}

	public void setDeclarationAnnotation(Class<? extends Annotation> declarationAnnotation) {
		this.declarationAnnotation = declarationAnnotation;
	}

	public void setCustomAnnotation(Class<? extends Annotation> customAnnotation) {
		this.customAnnotation = customAnnotation;
	}

	public void setBeanDefinitionPostProcessor(
			Class<? extends BeanDefinitionPostProcessor> beanDefinitionPostProcessor) {
		this.beanDefinitionPostProcessor = beanDefinitionPostProcessor;
	}

	public void setBeanPropertyValuesInitHook(Class<? extends BeanPropertyValuesInitHook> beanPropertyValuesInitHook) {
		this.beanPropertyValuesInitHook = beanPropertyValuesInitHook;
	}

	public void setFactoryBeanHook(Class<? extends FactoryBeanHook> factoryBeanHook) {
		this.factoryBeanHook = factoryBeanHook;
	}

	public CustomAnnotationScanner(BeanDefinitionRegistry registry) {
		super(registry);
	}

	@Override
	public void registerDefaultFilters() {
		addIncludeFilter(new AnnotationTypeFilter(declarationAnnotation));
	}

	@Override
	public Set<BeanDefinitionHolder> doScan(String... basePackages) {
		Set<BeanDefinitionHolder> beanDefinitions = super.doScan(basePackages);
		for (BeanDefinitionHolder holder : beanDefinitions) {
			if (null != beanDefinitionPostProcessor) {
				try {
					beanDefinitionPostProcessor.newInstance().postProcessBeanDefinition(holder,
							customAnnotationScanAttr);
					continue;
				} catch (InstantiationException | IllegalAccessException e) {
					throw new RuntimeException("BeanDefinitionPostProcessor throw error!", e);
				}
			}
			GenericBeanDefinition definition = (GenericBeanDefinition) holder.getBeanDefinition();
			MutablePropertyValues propertyValues = definition.getPropertyValues();
			propertyValues.add("innerClass", definition.getBeanClass());
			propertyValues.add("customAnnotation", customAnnotation);
			
			Assert.notNull(beanPropertyValuesInitHook, "BeanPropertyValuesInitHook is null!");
			try {
				beanPropertyValuesInitHook.newInstance().propertyValuesInit(propertyValues, definition.getBeanClass(), declarationAnnotation);
			} catch (InstantiationException | IllegalAccessException e) {
				throw new RuntimeException("BeanDefinitionPostProcessor throw error!", e);
			}
			
			Assert.notNull(factoryBeanHook, "FactoryBeanHook is null!");
			definition.setBeanClass(factoryBeanHook);
		}
		return beanDefinitions;
	}

	@Override
	public boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
		return beanDefinition.getMetadata().isIndependent();
	}

}
