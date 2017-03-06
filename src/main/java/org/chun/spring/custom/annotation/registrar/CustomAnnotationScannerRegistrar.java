package org.chun.spring.custom.annotation.registrar;

import org.chun.spring.custom.annotation.CustomAnnotationScan;
import org.chun.spring.custom.annotation.scanner.CustomAnnotationScanner;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;

public class CustomAnnotationScannerRegistrar implements ImportBeanDefinitionRegistrar, ResourceLoaderAware {

	private ResourceLoader resourceLoader;

	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
		
		CustomAnnotationScanner scanner = new CustomAnnotationScanner(registry);
		
		AnnotationAttributes annoAttrs = AnnotationAttributes
				.fromMap(importingClassMetadata.getAnnotationAttributes(CustomAnnotationScan.class.getName()));
		
		scanner.setCustomAnnotationScanAttr(annoAttrs);
		
		if (resourceLoader != null) {
			scanner.setResourceLoader(resourceLoader);
		}

		String[] basePackages = annoAttrs.getStringArray("basePackages");
		scanner.scan(basePackages);
	}

}
