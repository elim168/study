/**
 * 
 */
package com.elim.learn.spring.aop.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * pointcut表达式有execution、within、this、target、args、@target、@args、@within、@annotation
 * @author Elim
 * 2016年12月30日
 */
@Component
@Aspect
public class MyAspect {

	@Pointcut("execution(* add(..))")
	private void beforeAdd() {}
	
	@Before("beforeAdd()")
	public void before1() {
		System.out.println("-----------before-----------");
	}
	
	@Before("com.elim.learn.spring.aop.service.MyService.add()")
	public void before2() {
		System.out.println("-----------before2-----------");
	}
	
	/**
	 * 所有的add方法的外部执行时
	 */
	@Before("execution(* add() throws Exception)")
	public void beforeExecution() {
		System.out.println("-------------before execution---------------");
	}
	
	/**
	 * com.elim.learn包及其子包下所有类的非私有方法的外部执行时
	 */
	@Before("within(com.elim.learn.spring.aop.service.UserServiceImpl)")
	public void beforeWithin() {
		System.out.println("-------------before within---------------");
	}
	
	/**
	 * Spring AOP是基于代理的，生成的bean也是一个代理对象，this就表示生成的代理对象。
	 * 当前对象可以转换为IUserService时
	 */
	@Before("this(com.elim.learn.spring.aop.service.IUserService)")
	public void beforeThis() {
		System.out.println("-------------before this is IUserService---------------");
	}
	
	/**
	 * 当前对象可以转换为UserServiceImpl时
	 */
	@Before("this(com.elim.learn.spring.aop.service.UserServiceImpl)")
	public void beforeThis2() {
		System.out.println("-------------before this is UserServiceImpl---------------");
	}
	
	/**
	 * 代理的目标对象，即被代理的对象可以转换为IUserService时
	 */
	@Before("target(com.elim.learn.spring.aop.service.IUserService)")
	public void beforeTarget() {
		System.out.println("-------------before target is IUserService---------------");
	}
	
	/**
	 * 代理的目标对象可以转换为UserServiceImpl时
	 */
	@Before("target(com.elim.learn.spring.aop.service.UserServiceImpl)")
	public void beforeTarget2() {
		System.out.println("-------------before target is UserServiceImpl---------------");
	}
	
	/**
	 * 当调用的方法是没有参数时
	 */
	@Before("args(..)")
	public void beforeArgs() {
		System.out.println("-------------before args no argument---------------");
	}
	
	/**
	 * 当调用的方法接收两个参数且第一个参数是Integer、第二个是String时
	 */
	@Before("args(java.lang.Integer,java.lang.String)")
	public void beforeArgs2() {
		System.out.println("-------------before args with Integer and String---------------");
	}
	
	/**
	 * 匹配任意参数
	 */
	@Before("args(..)")
	public void beforeArgs3() {
		System.out.println("-------------before args with any argument---------------");
	}
	
	
	/**
	 * 当代理的目标对象所在类上拥有MyAnnotation注解时
	 */
	@Before("@target(com.elim.learn.spring.support.MyAnnotation)")
	public void beforeAtTarget() {
		System.out.println("-------------before @target---------------");
	}
	
	/**
	 * 当调用的方法包含的参数的类型是采用MyAnnotation注解标注的时
	 */
	@Before("@args(com.elim.learn.spring.support.MyAnnotation)")
	public void beforeAtArgs() {
		System.out.println("-------------before @args---------------");
	}
	
	/**
	 * 跟@target类型类似，当目标对象的类上拥有MyAnnotation注解时
	 * 暂时没有找到@within和@target之间的区别，网上有说@target需要目标类上有对应的注解，而@within是目标类或者父类上有指定的注解即可，
	 * 试了下都是只要目标类或父类上有指定的注解即可。
	 */
	@Before("@within(com.elim.learn.spring.support.MyAnnotation)")
	public void beforeAtWithin() {
		System.out.println("-------------before @within---------------");
	}
	
	/**
	 * 当前目标对象执行的方法上拥有MyAnnotation注解时
	 */
	@Before("@annotation(com.elim.learn.spring.support.MyAnnotation)")
	public void beforeAtAnnation() {
		System.out.println("-------------before @annation---------------");
	}
	
	/**
	 * 当执行的是id或name为userService的bean的方法时
	 */
	@Before("bean(userService)")
	public void beforeBeanName() {
		System.out.println("-------------before beanIdOrName--------------");
	}
	
	/**
	 * 当执行的是id或name为以user开头的bean的方法时
	 */
	@Before("bean(user*)")
	public void beforeBeanName2() {
		System.out.println("-------------before bean(user*)--------------");
	}
	
	@Before("!bean(userService1)")
	public void beforeBeanName3() {
		System.out.println("-------------before !bean(userService1)--------------");
	}
	
	@Before("bean(userService) && args()")
	public void beforeCombination1() {
		System.out.println("---------bean(userService) && args()---------");
	}
	
	@Before("bean(userService) && !args()")
	public void beforeCombination2() {
		System.out.println("---------bean(userService) && !args()---------");
	}
	
	@Before("bean(userService1) || args()")
	public void beforeCombination3() {
		System.out.println("---------------bean(userService1) || args()----------------");
	}
	
}
