package org.chun.spring.custom.annotation;

import java.lang.reflect.Method;

import org.chun.spring.custom.annotation.defaults.annotation.ReplaceMethodInvoker;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@CustomAnnotationScan("org.chun.spring.custom.annotation.service")
public class Application {
	
	@Bean
	public ReplaceMethodInvoker sayReplaceMethodInvoker(){
		return new ReplaceMethodInvoker() {
			
			@Override
			public Object invoke(Object proxy, Method method, Object[] arguments, MethodProxy methodProxy) throws Throwable {
				return "hahaha";
			}
		};
	}
	
}
