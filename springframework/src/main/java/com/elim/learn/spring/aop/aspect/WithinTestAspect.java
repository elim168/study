/**
 * 
 */
package com.elim.learn.spring.aop.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @author Elim
 * 2017年9月14日
 */
@Component
@Aspect
public class WithinTestAspect {

	@Around("@within(com.elim.learn.spring.bean.WithinAnnotation)")
	public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
		System.out.println("======准备调用方法：" +  pjp.getTarget().getClass().getName() + "." + pjp.getSignature().getName());
		return pjp.proceed();
	}
	
}
