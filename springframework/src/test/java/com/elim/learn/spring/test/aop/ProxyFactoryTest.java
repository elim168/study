/**
 * 
 */
package com.elim.learn.spring.test.aop;

import java.lang.reflect.Method;

import org.junit.Test;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import org.springframework.aop.framework.ProxyFactory;

import com.elim.learn.spring.aop.aspect.MyAspect;
import com.elim.learn.spring.aop.service.MyService;

/**
 * @author Elim
 * 2017年5月3日
 */
public class ProxyFactoryTest {

	/**
	 * 测试AspectjProxyFactory
	 */
	@Test
	public void testAspectJProxyFactory() {
		MyService myService = new MyService();
		AspectJProxyFactory proxyFactory = new AspectJProxyFactory(myService);
		proxyFactory.addAspect(MyAspect.class);
		proxyFactory.setProxyTargetClass(true);
		MyService proxy = proxyFactory.getProxy();
		proxy.add();
	}
	
	/**
	 * 测试ProxyFactory
	 */
	@Test
	public void testProxyFactory() {
		MyService myService = new MyService();
		ProxyFactory proxyFactory = new ProxyFactory(myService);
		proxyFactory.setProxyTargetClass(true);
		proxyFactory.addAdvice(new MethodBeforeAdvice() {

			@Override
			public void before(Method method, Object[] args, Object target) throws Throwable {
				System.out.println("执行目标方法调用之前的逻辑");
				//不需要手动去调用目标方法，Spring内置逻辑里面会调用目标方法
			}
			
		});;
		MyService proxy = (MyService) proxyFactory.getProxy();
		proxy.add();
	}
	
}
