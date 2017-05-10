/**
 * 
 */
package com.elim.learn.spring.aop.pointcut;

import java.lang.reflect.Method;

import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;

/**
 * 自定义Pointcut
 * @author Elim
 * 2017年5月8日
 */
public class MyCustomPointcut implements Pointcut {

	@Override
	public ClassFilter getClassFilter() {
		return new MyCustomClassFilter();
	}

	@Override
	public MethodMatcher getMethodMatcher() {
		return new MyCustomMethodMatcher();
	}
	
	private static class MyCustomClassFilter implements ClassFilter {

		@Override
		public boolean matches(Class<?> clazz) {
			//实现自己的判断逻辑，这里简单的判断对应Class的名称是以Service结尾的就表示匹配
			return clazz.getName().endsWith("Service");
		}
		
	}
	
	private static class MyCustomMethodMatcher implements MethodMatcher {

		@Override
		public boolean matches(Method method, Class<?> targetClass) {
			//实现方法匹配逻辑
			return method.getName().startsWith("find");
		}

		@Override
		public boolean isRuntime() {
			return false;
		}

		@Override
		public boolean matches(Method method, Class<?> targetClass, Object[] args) {
			return false;
		}
		
	}

}
