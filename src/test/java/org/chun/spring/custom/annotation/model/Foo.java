package org.chun.spring.custom.annotation.model;

import org.springframework.stereotype.Component;

@Component
public class Foo {
	
	private String bar = "bar";

	public String getBar() {
		return bar;
	}

	public void setBar(String bar) {
		this.bar = bar;
	}
	
	
	
}
