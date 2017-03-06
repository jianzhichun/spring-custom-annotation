package org.chun.spring.custom.annotation.defaults.annotation.hook;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.chun.spring.custom.annotation.defaults.annotation.MethodReplace;
import org.chun.spring.custom.annotation.defaults.annotation.Replace;
import org.chun.spring.custom.annotation.hook.BeanPropertyValuesInitHook;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.ReflectionUtils.MethodCallback;

public class MethodReplaceBeanPropertyValuesInitHook implements BeanPropertyValuesInitHook {

	@Override
	public void propertyValuesInit(MutablePropertyValues propertyValues, Class<?> targetClass,
			Class<? extends Annotation> declarationAnnotation) {
		Assert.isInstanceOf(declarationAnnotation, MethodReplace.class, "The declarationAnnotation is not instanceof @MethodReplace");
		MethodReplace methodReplace = targetClass.getDeclaredAnnotation(MethodReplace.class);
		Assert.notNull(methodReplace, "targetClass has not @MethodReplace");
		
		Map<String, RuntimeBeanReference> methodReplaceMap = new HashMap<>();
		ReflectionUtils.doWithMethods(targetClass, new MethodCallback() {
			
			@Override
			public void doWith(Method method) throws IllegalArgumentException, IllegalAccessException {
				if(method.isAnnotationPresent(Replace.class)){
					Replace replace = method.getAnnotation(Replace.class);
					methodReplaceMap.put(method.getName(), new RuntimeBeanReference(replace.replaceMethodInvokerRef()));
				}
				
			}
		});
		propertyValues.add("allMethodInvoker", new RuntimeBeanReference(methodReplace.replaceMethodInvokerRef()));
		propertyValues.add("isSingleton", methodReplace.isSingleton());
		propertyValues.add("methodReplaceMap", methodReplaceMap);
	}

}
