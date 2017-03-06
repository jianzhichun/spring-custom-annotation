package org.chun.spring.custom.annotation;

import static org.junit.Assert.*;

import org.chun.spring.custom.annotation.service.TestService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class)
public class ApplicationTest {
	
	@Autowired TestService testService;
	
	@Test
	public void test1(){
		assertEquals("hello", testService.say());
		assertEquals("hahaha", testService.say1());
		assertEquals("hello bar", testService.say2());
	}
	
}
