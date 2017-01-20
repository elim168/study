/**
 * 
 */
package com.elim.learn.spring.test.aop;

import java.lang.reflect.Method;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.elim.learn.spring.aop.aspect.AdviceWithParamAspect;
import com.elim.learn.spring.aop.service.IUserService;

/**
 * 
 * @author Elim
 * 2017年1月14日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class AdviceWithParamTest {

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
		Method[] methods = AdviceWithParamAspect.class.getDeclaredMethods();
		System.out.println("==========================================");
		for (Method method : methods) {
			System.out.println(method);
		}
	}
	
	@Test
	public void testDelete() {
		this.userService.delete("张三", 1);
	}
	
}
