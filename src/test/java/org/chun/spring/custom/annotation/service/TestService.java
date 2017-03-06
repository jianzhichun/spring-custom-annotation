package org.chun.spring.custom.annotation.service;

import java.lang.reflect.Method;

import org.chun.spring.custom.annotation.defaults.annotation.MethodReplace;
import org.chun.spring.custom.annotation.defaults.annotation.Replace;
import org.chun.spring.custom.annotation.defaults.annotation.ReplaceMethodInvoker;
import org.chun.spring.custom.annotation.model.Foo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.proxy.MethodProxy;

@MethodReplace("sayReplaceMethodInvoker")
public abstract class TestService {
	
	@Autowired
	private Foo foo;
	
	public Foo getFoo() {
		return foo;
	}
	@Replace(org.chun.spring.custom.annotation.method.SayReplaceMethodInvoker.class)
	public String say(){
		return "11";
	};
	@Replace
	public abstract String say1();
	
	@Replace(org.chun.spring.custom.annotation.method.SayReplaceMethodInvoker2.class)
	public abstract String say2();
	
	public class SayReplaceMethodInvoker extends ReplaceMethodInvoker{

		@Override
		public Object invoke(Object proxy, Method method, Object[] arguments, MethodProxy methodProxy)
				throws Throwable {
			return "hello";
		}
		
	}
	
}
