/**
 * 
 */
package com.elim.learn.spring.test.aop;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.elim.learn.spring.aop.service.IUserService;

/**
 * 
 * @author Elim
 * 2017年1月14日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class AdviceTest {

	@Autowired
	@Qualifier("userService")
	private IUserService userService;
	
	@Test
	public void testAdd() throws Exception {
		this.userService.add();
	}
	
	@Test
	public void testFind() {
		this.userService.findById(10);
	}
	
	@Test
	public void testAfterThrowing() {
		this.userService.findById(null);
	}
	
}
