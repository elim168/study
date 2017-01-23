/**
 * 
 */
package com.elim.learn.spring.aop.advice;

import java.lang.reflect.Method;

import org.springframework.aop.MethodBeforeAdvice;

/**
 * 方法调用前进行日志输出
 * @author Elim
 * 2017年1月23日
 */
public class LogBeforeAdvice implements MethodBeforeAdvice {

	/* (non-Javadoc)
	 * @see org.springframework.aop.MethodBeforeAdvice#before(java.lang.reflect.Method, java.lang.Object[], java.lang.Object)
	 */
	@Override
	public void before(Method method, Object[] args, Object target) throws Throwable {
		System.out.println("===============before advice start==============");
		System.out.println("method: " + method);
		System.out.println("args: " + args);
		System.out.println("target: " + target);
		System.out.println("===============before advice end================");
	}

}
