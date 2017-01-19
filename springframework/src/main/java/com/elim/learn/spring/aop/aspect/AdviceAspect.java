/**
 * 
 */
package com.elim.learn.spring.aop.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 介绍Advice类型
 * @author Elim
 * 2017年1月14日
 */
@Component
@Aspect
public class AdviceAspect {

	/**
	 * before将在切入点方法执行前执行
	 * @param joinPoint 所有的Advice都可以把它的第一个参数定义为JoinPoint，通过它我们可以获取到一些与当前目标方法调用有关的信息
	 */
	@Before("bean(userService)")
	public void before(JoinPoint joinPoint) {
		System.out.println("-----before with pointcut expression: bean(userService)------");
		/*joinPoint.getArgs();//获取当前目标方法调用传递的参数
		joinPoint.getSignature();//获取当前目标方法的签名，通过它可以获取到目标方法名
		joinPoint.getThis();//获取AOP生成的代理对象
		joinPoint.getTarget();//获取被代理对象，即目标对象
		System.out.println(joinPoint.getArgs());
		System.out.println(joinPoint.getSignature().getName());
		System.out.println(joinPoint.getThis().getClass());
		System.out.println(joinPoint.getTarget().getClass());
		System.out.println(joinPoint.toString());*/
	}
	
	@Before("bean(userService)")
	public void before2(JoinPoint joinPoint) {
		System.out.println("-----before2 with pointcut expression: bean(userService)------");
	}
	
	
	/**
	 * AfterReturning将在切入点方法正常执行完后执行，如果需要在Advice中获取返回值，则可以给该Advice方法加上一个参数，同时指定@AfterReturning的returning属性的值为参数名
	 * @param returnValue
	 */
	@AfterReturning(value="bean(userService)", returning="returnValue")
	public void afterReturning(Object returnValue) {
		System.out.println("-----after returning with pointcut expression: bean(userService)------");
		System.out.println("-----return value is: " + returnValue);
	}
	
	/**
	 * AfterThrowing类型的Advice将在目标方法在调用出现异常时执行。如果我们想获取到这个异常对象，可以给AfterThrowing类型的
	 * Advice一个Exception类型的参数，同时通过@AfterThrowing的属性throwing指定参数名。
	 * 需要注意的是如果我们的目标方法在执行的时候抛出的异常被Around Advice捕获了但是又没有向外抛出异常的时候，AfterThrowing类型
	 * 的Advice是不会执行的。换句话说我们在Around Advice中抛出的异常也将触发AfterThrowing类型的Advice的执行。
	 * @param ex
	 */
	@Order(1)
	@AfterThrowing(value="bean(userService)", throwing="ex")
	public void afterThrowing(Exception ex) {
		System.out.println("-----after throwing with pointcut expression: bean(userService)------" + ex);
	}
	
	/**
	 * after会在切入点方法整体执行完成后执行，无论其是正常返回还是对外抛出了异常
	 */
	@After("bean(userService)")
	public void after() {
		System.out.println("-----after with pointcut expression: bean(userService)------");
	}
	
	/**
	 * 
	 * Around类型的Advice我们应该给定一个ProceedingJoinPoint参数，而且这个参数必须是Advice方法的第一个参数，
	 * 然后在自己的方法体里面调用ProceedingJoinPoint参数 的proceed()方法以便目标方法能顺利的执行。
	 * 我们也可以根据需要调用ProceedingJoinPoint参数的proceed(Object[] args)方法，这种情况将改变原有方法调用的
	 * 参数传递，会把我们调用proceed方法时传递的参数args作为参数调用对应的目标方法。
	 * Around类型的Advice的返回值会被当做最终的目标方法调用的返回值传递给目标方法的调用者，所以我们也可以根据需要在Around
	 * Advice中改变目标方法的返回值。
	 * @throws Throwable 
	 */
	@Around("bean(userService)")
	public Object around(ProceedingJoinPoint pjp) throws Throwable {
		System.out.println("-----around with pointcut expression: bean(userService)------");
		System.out.println("---------------------调用前---------------------");
		Object result = null;
		Object[] args = pjp.getArgs();
		if (args.length == 1 && args[0] == null) {
			throw new IllegalArgumentException();
//			try {
//			result = pjp.proceed();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
		} else {
			Object[] params = new Object[]{15};
			result = pjp.proceed(params);//可以调整目标方法调用时传递的参数
		}
		System.out.println("---------------------调用后---------------------");
		return result;
	}
	
}
