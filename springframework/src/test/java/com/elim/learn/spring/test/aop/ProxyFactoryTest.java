/**
 * 
 */
package com.elim.learn.spring.test.aop;

import java.lang.reflect.Method;

import org.junit.Test;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import org.springframework.aop.framework.ProxyFactory;

import com.elim.learn.spring.aop.advisor.MyAdvisor;
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
//		proxyFactory.addAspect(aspectInstance);//指定一个切面实例也是ok的
		proxyFactory.setProxyTargetClass(true);//是否需要使用CGLIB代理
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
	
	@Test
	public void testProxyFactory2() {
		MyService myService = new MyService();
		ProxyFactory proxyFactory = new ProxyFactory(myService);
		proxyFactory.setExposeProxy(true);//指定对外发布代理对象，即在目标对象方法中可以通过AopContext.currentProxy()访问当前代理对象。
		proxyFactory.addAdvisor(new MyAdvisor());
		proxyFactory.addAdvisor(new MyAdvisor());//多次指定Advisor将同时应用多个Advisor
		MyService proxy = (MyService) proxyFactory.getProxy();
		proxy.add();
	}
	
}
