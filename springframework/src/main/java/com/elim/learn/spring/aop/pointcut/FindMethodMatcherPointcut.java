/**
 * 
 */
package com.elim.learn.spring.aop.pointcut;

import java.lang.reflect.Method;

import org.springframework.aop.support.StaticMethodMatcherPointcut;

/**
 * 自定义pointcut，匹配所有的方法名以find开头的方法调用
 * @author Elim
 * 2017年5月5日
 */
public class FindMethodMatcherPointcut extends StaticMethodMatcherPointcut {

	@Override
	public boolean matches(Method method, Class<?> targetClass) {
		return method.getName().startsWith("find");
	}

}
