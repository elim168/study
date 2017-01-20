/**
 * 
 */
package com.elim.learn.spring.aop.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.elim.learn.spring.aop.service.IUserService;
import com.elim.learn.spring.support.MyAnnotation;

/**
 * 介绍Advice中获取方法参数的情形
 * @author Elim
 * 2017年1月19日
 */
@Component
@Aspect
@Order(1)
public class AdviceWithParamAspect {

	/**
	 * before将在切入点方法执行前执行
	 * @param joinPoint 所有的Advice都可以把它的第一个参数定义为JoinPoint，通过它我们可以获取到一些与当前目标方法调用有关的信息
	 */
	@Before("bean(userService) && execution(* findById(java.lang.Integer))")
	public void before() {
		System.out.println(this.getClass().getName()+"-----before with pointcut expression: bean(userService)------");
	}
	
	/**
	 * 非常精确的匹配就是这样的
	 * @param id
	 */
	@Before(value="bean(userService) && execution(* findById(java.lang.Integer)) && args(id)", argNames="id")
	public void beforeWithParam(JoinPoint joinPoint, Integer id) {
		System.out.println(this.getClass().getName()+" ID is : " + id);
	}
	
	/**
	 * 拦截id或name为userService的bean中只含有一个方法参数，且这个参数是int类型的方法。
	 * 这种情况下会把实际方法调用的参数传递给Advice的方法参数id。
	 * 在Pointcut表达式中通过args(adviceParamName)指定拦截的目标方法参数类型必须满足adviceParamName对应的Advice处理方法参数的类型，
	 * 如下就通过args(id)指定目标方法必须接收一个参数，且参数类型必须是下面Advice方法的参数id对应的类型，即int类型或Integer类型。需要说明的是
	 * 在编译的字节码中不含DEBUG信息或者args()中指定的参数名与Advice方法参数名不一致时需要通过argNames来指定Pointcut表达式中args()指定的参数对应
	 * Advice方法参数的顺序
	 * @param id
	 */
	@Before(value="bean(userService) && args(id,..)", argNames="id")
	public void beforeWithParam2(int id) {
		System.out.println(this.getClass().getName()+" ID is : " + id);
	}
	
	/**
	 * args(name, sex)指定拦截的方法需要有两个参数，这两个参数的类型由argNames="sex,name"来指定，argsNames里面指定的参数名需要与args(name, sex)中指定的一致，
	 * argsNames="sex, name"指定的参数的顺序与Advice处理方法中对应的参数类型的顺序必须一致，即args()中指定的参数sex与Advice方法的第一个参数sex1的类型一致，
	 * args()中指定的参数name与Advice方法的第二个参数name1的类型一致。
	 * @param sex
	 * @param name
	 */
	@Before(value="bean(userService) && args(name, sex)", argNames="sex, name")
	public void beforeWithParam3(int sex1, String name1) {
		System.out.println("sex is : " + sex1);
		System.out.println("name is : " + name1);
	}
	
	/**
	 * 传递this对象
	 * @param userService
	 */
	@Before("this(userService)")
	public void beforeWithParam4(IUserService userService) {
		//this对象应该是一个代理对象
		System.out.println(this.getClass().getName()+"==============传递this对象： " + userService.getClass());
	}
	
	@Before("this(userService) && args(id)")
	public void beforeWithParam5(IUserService userService, int id) {
		System.out.println(this.getClass().getName()+"===========" + id + "==============" + userService.getClass());
	}
	
	/**
	 * 传递target对象
	 * @param userService
	 */
	@Before("target(userService)")
	public void beforeWithParam6(IUserService userService) {
		System.out.println(this.getClass().getName()+"==============传递target对象： " + userService.getClass());
	}
	
	/**
	 * 传递标注在方法上的Annotation，需要传递标注在其它地方的Annotation也是一样的
	 * @param annotation
	 */
	@Before("@annotation(annotation)")
	public void beforeWithParam7(MyAnnotation annotation) {
		System.out.println(this.getClass().getName()+"==============传递标注在方法上的annotation： " + annotation.annotationType().getName());
	}
	
	/**
	 * AfterReturning将在切入点方法正常执行完后执行，如果需要在Advice中获取返回值，则可以给该Advice方法加上一个参数，同时指定@AfterReturning的returning属性的值为参数名
	 * @param returnValue
	 */
	@AfterReturning(value="bean(userService) && args(id)", returning="returnValue")
	public void afterReturning(Object returnValue, int id) {
		System.out.println(this.getClass().getName()+"-----after returning with pointcut expression: bean(userService)------");
		System.out.println(this.getClass().getName()+"-----return value is: " + returnValue);
		System.out.println("ID is : " + id);
	}
	
}
