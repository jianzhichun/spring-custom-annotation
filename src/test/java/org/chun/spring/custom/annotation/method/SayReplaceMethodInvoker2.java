package org.chun.spring.custom.annotation.method;

import java.lang.reflect.Method;

import org.chun.spring.custom.annotation.defaults.annotation.ReplaceMethodInvoker;
import org.chun.spring.custom.annotation.service.TestService;
import org.springframework.cglib.proxy.MethodProxy;

public class SayReplaceMethodInvoker2 extends ReplaceMethodInvoker{

	@Override
	public Object invoke(Object proxy, Method method, Object[] arguments, MethodProxy methodProxy)
			throws Throwable {
		TestService testService = null;
		if(proxy instanceof TestService){
			testService = (TestService)proxy;
		}
		return "hello " + testService.getFoo().getBar();
	}
	
}