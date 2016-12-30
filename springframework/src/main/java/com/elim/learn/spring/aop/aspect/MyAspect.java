/**
 * 
 */
package com.elim.learn.spring.aop.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author Elim
 * 2016年12月30日
 */
@Component
@Aspect
public class MyAspect {

	@Pointcut("execution(* add(..))")
	private void beforeAdd() {}

	@Before("com.elim.learn.spring.aop.aspect.MyAspect.beforeAdd()")
	public void before() {
		System.out.println("---------before-----------");
	}
	
}
