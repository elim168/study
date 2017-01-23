/**
 * 
 */
package com.elim.learn.spring.aop.advice;

import java.lang.reflect.Method;

import org.springframework.aop.AfterReturningAdvice;

import com.elim.learn.spring.common.model.User;

/**
 * @author Elim
 * 2017年1月23日
 */
public class LogAfterReturningAdvice implements AfterReturningAdvice {

	/* (non-Javadoc)
	 * @see org.springframework.aop.AfterReturningAdvice#afterReturning(java.lang.Object, java.lang.reflect.Method, java.lang.Object[], java.lang.Object)
	 */
	@Override
	public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
		System.out.println("==============调用成功返回，返回值是：" + returnValue);
		System.out.println("Method: " + method);
		if (returnValue instanceof User) {
			//不能修改返回值，但可以修改返回值的某些属性，因为是对象引用
			((User) returnValue).setName("modifyedName");
		}
	}

}
