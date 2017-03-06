package org.chun.spring.custom.annotation.defaults.annotation.hook;

import java.lang.reflect.Method;
import java.util.Map;

import org.chun.spring.custom.annotation.defaults.annotation.ReplaceMethodInvoker;
import org.chun.spring.custom.annotation.hook.FactoryBeanHook;
import org.springframework.cglib.core.SpringNamingPolicy;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.InvocationHandler;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.cglib.proxy.Proxy;

public class MethodReplaceFactoryBeanHook<T> extends FactoryBeanHook<T> {

	private boolean isSingleton;
	private Map<String, ReplaceMethodInvoker> methodReplaceMap;
	private ReplaceMethodInvoker allMethodInvoker;
	private MethodReplaceProxy methodReplaceProxy = new MethodReplaceProxy();
	
	public void setSingleton(boolean isSingleton) {
		this.isSingleton = isSingleton;
	}
	public void setMethodReplaceMap(Map<String, ReplaceMethodInvoker> methodReplaceMap) {
		this.methodReplaceMap = methodReplaceMap;
	}
	
	public void setAllMethodInvoker(ReplaceMethodInvoker allMethodInvoker) {
		this.allMethodInvoker = allMethodInvoker;
	}
	@SuppressWarnings("unchecked")
	@Override
	public T getObject() throws Exception {
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

	@Override
	public Class<?> getObjectType() {
		return innerClass;
	}

	@Override
	public boolean isSingleton() {
		return isSingleton;
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
				ReplaceMethodInvoker perMethodInvoker = methodReplaceMap.get(method.getName());
				if(null != perMethodInvoker){
					return perMethodInvoker.invoke(proxy, method, arguments, methodProxy);	
				}
				if(null != allMethodInvoker){
					return allMethodInvoker.invoke(proxy, method, arguments, methodProxy);
				}
			}
			return allMethodInvoker.formerInvoke(proxy, method, arguments, methodProxy);
		}
	}

}
