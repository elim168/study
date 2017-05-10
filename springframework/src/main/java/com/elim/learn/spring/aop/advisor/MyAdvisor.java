/**
 * 
 */
package com.elim.learn.spring.aop.advisor;

import java.lang.reflect.Method;

import org.aopalliance.aop.Advice;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.PointcutAdvisor;

/**
 * 简单的实现自己的PointcutAdvisor
 * @author Elim 2017年5月9日
 */
public class MyAdvisor implements PointcutAdvisor {

	@Override
	public Advice getAdvice() {
		return new MethodBeforeAdvice() {

			@Override
			public void before(Method method, Object[] args, Object target) throws Throwable {
				System.out.println("BeforeAdvice实现，在目标方法被调用前调用，目标方法是：" + method.getDeclaringClass().getName() + "."
						+ method.getName());
			}
		};
	}

	@Override
	public boolean isPerInstance() {
		return true;
	}

	@Override
	public Pointcut getPointcut() {
		//匹配所有的方法调用
		return Pointcut.TRUE;
	}

}
