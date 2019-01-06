/**
 * 
 */
package com.elim.learn.spring.aop.advice;

import java.lang.reflect.Method;

import org.springframework.aop.ThrowsAdvice;

/**
 * ThrowsAdvice只是一个标记用的接口，没有定义任何方法，具体的处理方法将由用户自己定义，方法格式如下：
 * afterThrowing([Method, args, target], subclassOfThrowable)
 * 方法名必须是afterThrowing，前三个参数可以忽略
 * @author Elim
 * 2017年1月23日
 */
public class LogThrowsAdvice implements ThrowsAdvice {

	/**
	 * 处理IllegalArgumentException
	 * @param e
	 */
	public void afterThrowing(IllegalArgumentException e) {
		System.out.println("=====================方法调用异常，抛出了IllegalArgumentException");
	}
	
	/**
	 * 处理NumberFormatException
	 * @param e
	 */
	public void afterThrowing(NumberFormatException e) {
		System.out.println("=====================方法调用异常，抛出了NumberFormatException");
	}
	
	/**
	 * 处理其它所有的异常
	 * @param method
	 * @param args
	 * @param target
	 * @param e
	 */
	public void afterThrowing(Method method, Object[] args, Object target, Exception e) {
		System.out.println("=====================方法调用异常了，" + e);
		System.out.println("Method: " + method);
		System.out.println("Args: " + args);
		System.out.println("Target: " + target);
	}
	
}
