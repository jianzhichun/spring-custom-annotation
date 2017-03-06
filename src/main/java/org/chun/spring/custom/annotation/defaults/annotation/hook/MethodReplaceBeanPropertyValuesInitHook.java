package org.chun.spring.custom.annotation.defaults.annotation.hook;

import java.lang.annotation.Annotation;

import org.chun.spring.custom.annotation.defaults.annotation.MethodReplace;
import org.chun.spring.custom.annotation.hook.BeanPropertyValuesInitHook;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public class MethodReplaceBeanPropertyValuesInitHook implements BeanPropertyValuesInitHook {

	@Override
	public void propertyValuesInit(MutablePropertyValues propertyValues, Class<?> targetClass,
			Class<? extends Annotation> declarationAnnotation) {
		Assert.isTrue(MethodReplace.class.equals(declarationAnnotation),
				"The declarationAnnotation is not instanceof @MethodReplace");
		MethodReplace methodReplace = targetClass.getDeclaredAnnotation(MethodReplace.class);
		Assert.notNull(methodReplace, "targetClass has not @MethodReplace");

		if(!StringUtils.isEmpty(methodReplace.value())){
			propertyValues.add("allMethodInvoker", new RuntimeBeanReference(methodReplace.value()));
		}
		propertyValues.add("singleton", methodReplace.isSingleton());
	}

}
