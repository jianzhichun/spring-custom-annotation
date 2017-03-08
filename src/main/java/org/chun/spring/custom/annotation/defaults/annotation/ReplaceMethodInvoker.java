package org.chun.spring.custom.annotation.defaults.annotation;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.chun.spring.custom.annotation.helper.Defaults;
import org.springframework.cglib.proxy.MethodProxy;

public abstract class ReplaceMethodInvoker {
	
	private Object proxy;
	private Method method;
	private Object[] arguments;
	private MethodProxy methodProxy;
	
	public Object formerInvoke() throws Throwable {
		if (Modifier.isAbstract(method.getModifiers()))
			return method.getReturnType().isPrimitive() ? Defaults.defaultValue(method.getReturnType()) : null;
		return null != methodProxy ? methodProxy.invokeSuper(proxy, arguments) : method.invoke(proxy, arguments);
	}
	
	public Object invocation(Object proxy, Method method, Object[] arguments, MethodProxy methodProxy) throws Throwable {
		this.proxy = proxy;
		this.method = method;
		this.arguments = arguments;
		this.methodProxy = methodProxy;
		return invoke(proxy, method, arguments, methodProxy);
	}
	
	public abstract Object invoke(Object proxy, Method method, Object[] arguments, MethodProxy methodProxy) throws Throwable;

}
