package org.chun.spring.custom.annotation.method;

import java.lang.reflect.Method;

import org.chun.spring.custom.annotation.defaults.annotation.ReplaceMethodInvoker;
import org.springframework.cglib.proxy.MethodProxy;

public class SayReplaceMethodInvoker extends ReplaceMethodInvoker{

	@Override
	public Object invoke(Object proxy, Method method, Object[] arguments, MethodProxy methodProxy)
			throws Throwable {
		return "hello";
	}
	
}