/**
 * 
 */
package com.elim.learn.spring.aop.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.DeclareParents;

import com.elim.learn.spring.aop.service.CommonParent;
import com.elim.learn.spring.aop.service.CommonParentImpl;

/**
 * @DeclareParents测试
 * @author Elim
 * 2017年1月22日
 */
//@Component
@Aspect
public class DeclareParentsAspect {

	/**
	 * 声明父类，value指定表达式对应的所有的类将实现CommonParent接口，且该接口的默认实现类是CommonParentImpl，
	 * 这样我们在运行时就可以把value指定表达式对应的类对应的bean当做CommonParent来使用了。
	 */
	@DeclareParents(value="com.elim.learn.spring.aop.service..*", defaultImpl=CommonParentImpl.class)
	private CommonParent commonParent;
	
	@Before("bean(userService) && this(commonParent)")
	public void beforeUserService(JoinPoint joinPoint, CommonParent commonParent) {
		commonParent.doSomething();
	}
	
}
