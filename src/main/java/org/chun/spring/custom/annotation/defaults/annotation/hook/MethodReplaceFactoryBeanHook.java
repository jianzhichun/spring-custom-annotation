package org.chun.spring.custom.annotation.defaults.annotation.hook;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.chun.spring.custom.annotation.defaults.annotation.Replace;
import org.chun.spring.custom.annotation.defaults.annotation.ReplaceMethodInvoker;
import org.chun.spring.custom.annotation.helper.Defaults;
import org.chun.spring.custom.annotation.hook.FactoryBeanHook;
import org.springframework.cglib.core.SpringNamingPolicy;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.InvocationHandler;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.cglib.proxy.Proxy;

public class MethodReplaceFactoryBeanHook<T> extends FactoryBeanHook<T> {

	private boolean singleton;
	private ReplaceMethodInvoker allMethodInvoker;
	private MethodReplaceProxy methodReplaceProxy = new MethodReplaceProxy();
	
	public void setSingleton(boolean singleton) {
		this.singleton = singleton;
	}
	
	public void setAllMethodInvoker(ReplaceMethodInvoker allMethodInvoker) {
		this.allMethodInvoker = allMethodInvoker;
	}
	@SuppressWarnings("unchecked")
	@Override
	public T getObject() throws Exception {
		Class<T> innerClass = getObjectType();
		if (innerClass.isInterface()) {
			return (T) methodReplaceProxy.newInterfaceInstance(innerClass);
		} else {
			Enhancer enhancer = new Enhancer();
			enhancer.setSuperclass(innerClass);
			enhancer.setNamingPolicy(SpringNamingPolicy.INSTANCE);
			enhancer.setCallback(methodReplaceProxy.newReplaceMethod());
			return (T) enhancer.create();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Class<T> getObjectType() {
		try {
			return (Class<T>) Class.forName(innerClassName);
		} catch (ClassNotFoundException e) {
			return null;
		}
	}

	@Override
	public boolean isSingleton() {
		return singleton;
	}

	class MethodReplaceProxy {

		@SuppressWarnings({ "unchecked", "rawtypes" })
		T newInterfaceInstance(Class<T> innerClass) {
			ClassLoader classLoader = innerClass.getClassLoader();
			Class[] interfaces = new Class[] { innerClass };
			return (T) Proxy.newProxyInstance(classLoader, interfaces, new InvocationHandler() {
				
				@Override
				public Object invoke(Object proxy, Method method, Object[] arguments) throws Throwable {
					return invocation(proxy, method, arguments, null);
				}
			});
		}

		MethodInterceptor newReplaceMethod() {
			return new MethodInterceptor() {
				
				@Override
				public Object intercept(Object proxy, Method method, Object[] arguments, MethodProxy methodProxy) throws Throwable {
					return invocation(proxy, method, arguments, methodProxy);
				}
			};
		}

		protected Object invocation(Object proxy, Method method, Object[] arguments, MethodProxy methodProxy) throws Throwable {
			if(method.isAnnotationPresent(customAnnotation)){
				if(Replace.class.equals(customAnnotation)){
					Replace replace = method.getAnnotation(Replace.class);
					if(!replace.value().equals(ReplaceMethodInvoker.class)){
						return replace.value().newInstance().invocation(proxy, method, arguments, methodProxy);
					}
				}
				if(null != allMethodInvoker){
					return allMethodInvoker.invocation(proxy, method, arguments, methodProxy);
				}
			}
			if (Modifier.isAbstract(method.getModifiers()))
				return method.getReturnType().isPrimitive() ? Defaults.defaultValue(method.getReturnType()) : null;
			return null != methodProxy ? methodProxy.invokeSuper(proxy, arguments) : method.invoke(proxy, arguments);
		}
	}

}
