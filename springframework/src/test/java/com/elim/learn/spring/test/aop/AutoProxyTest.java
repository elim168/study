/**
 * 
 */
package com.elim.learn.spring.test.aop;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.elim.learn.spring.aop.service.IUserService;
import com.elim.learn.spring.aop.service.MyService;

/**
 * @author Elim
 * 2017年5月12日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/applicationContext-aop.xml")
public class AutoProxyTest {

	@Autowired
	private IUserService userService;
	@Autowired
	private MyService myService;
	
	@Test
	public void testUserService() {
		userService.findById(1);
	}
	
	@Test
	public void testMyService() {
		myService.find();
	}
	
}
