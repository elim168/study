/**
 * 
 */
package com.elim.learn.spring.aop.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Pointcut;

import com.elim.learn.spring.aop.service.CommonParent;

/**
 * 基于XML配置的切面类
 * @author Elim
 * 2017年1月22日
 */
public class SchemaBasedAspect {

	@Pointcut("bean(userService)")
	public void pointcut() {
		
	}
	
	public void doBefore1(int id) {
		System.out.println("======================doBefore======================" + id);
	}
	
	public void doBefore(CommonParent commonParent) {
		System.out.println("======================doBefore======================");
		commonParent.doSomething();
	}
	
	public void doAfterReturning(Object returnValue) {
		System.out.println("===================do After Return====================" + returnValue);
	}
	
	public void doAfterThrowing(Exception ex) {
		System.out.println("=========================do After Throw with exception : " + ex);
	}
	
	public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
		System.out.println("=================Around Start=================");
		try {
			Object result = pjp.proceed();
			System.out.println(pjp.getThis().getClass());
			System.out.println("==================成功执行了");
			return result;
		} catch (Exception e) {
			System.out.println("==========================调用异常了");
			throw e;
		} finally {
			System.out.println("=================Around End===================");
		}
	}
	
}
