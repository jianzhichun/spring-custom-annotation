package org.chun.spring.custom.annotation.defaults.annotation;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.chun.spring.custom.annotation.helper.Defaults;
import org.springframework.cglib.proxy.MethodProxy;

public abstract class ReplaceMethodInvoker {
	
	
	public Object formerInvoke(Object proxy, Method method, Object[] arguments, MethodProxy methodProxy) throws Throwable {
		if (Modifier.isAbstract(method.getModifiers()))
			return method.getReturnType().isPrimitive() ? Defaults.defaultValue(method.getReturnType()) : null;
		return null != methodProxy ? methodProxy.invokeSuper(proxy, arguments) : method.invoke(proxy, arguments);
	}
	
	public abstract Object invoke(Object proxy, Method method, Object[] arguments, MethodProxy methodProxy) throws Throwable;

}
