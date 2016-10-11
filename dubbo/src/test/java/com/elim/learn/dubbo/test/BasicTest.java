/**
 * 
 */
package com.elim.learn.dubbo.test;

import java.io.IOException;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.elim.learn.dubbo.service.UserService;

/**
 * @author Elim
 * 2016年10月11日
 *
 */
public class BasicTest {

	@Test
	public void testServer() throws IOException {
		ApplicationContext context = new ClassPathXmlApplicationContext("application-server.xml");
		System.out.println(context.getBeanDefinitionCount());
		System.in.read();
	}
	
	@Test
	public void testClient() {
		ApplicationContext context = new ClassPathXmlApplicationContext("application-client.xml");
		UserService userService = (UserService) context.getBean("userService");
		userService.sayHello("张三");
	}
	
}
