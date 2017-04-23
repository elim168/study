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
import com.elim.learn.spring.aop.service.MyService;
import com.elim.learn.spring.support.ArgWithAnnotation;

/**
 * 测试pointcut表达式。
 * 表达式类型有：execution、within、this、target、args、@target、@args、@within、@annotation
 * @author Elim
 * 2016年12月30日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class PointcutTest {

	@Autowired
	private MyService service;
	@Autowired
	@Qualifier("userService")
	private IUserService userService;
	
	@Test
	public void testAdd() {
		service.add();
	}
	
	@Test
	public void testExpression() throws Exception {
		this.userService.add();
		
	}
	
	/**
	 * 测试多方法参数的情况
	 */
	@Test
	public void testExpression2() {
		int org = 1;
		String name = "ABC";
		this.userService.add(org, name);
	}
	
	/**
	 * 测试方法参数类型上有指定的注解
	 */
	@Test
	public void testExpression3() {
		ArgWithAnnotation arg = null;
		this.userService.add(arg);
	}
	
	@Test
	public void testProxy() {
		this.service.add();
		System.out.println(this.service.getClass());
	}
	
}
