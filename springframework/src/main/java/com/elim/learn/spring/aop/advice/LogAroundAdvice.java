/**
 * 
 */
package com.elim.learn.spring.aop.advice;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @author Elim
 * 2017年1月23日
 */
public class LogAroundAdvice implements MethodInterceptor {

	/* (non-Javadoc)
	 * @see org.aopalliance.intercept.MethodInterceptor#invoke(org.aopalliance.intercept.MethodInvocation)
	 */
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		System.out.println("=============================方法调用开始===" + invocation.getMethod());
		try {
			Object result = invocation.proceed();
			System.out.println("=============================方法调用正常结束===" + invocation.getMethod());
			return result;
		} catch (Exception e) {
			System.out.println("=============================方法调用异常===" + invocation.getMethod());
			throw e;
		}
	}

}
