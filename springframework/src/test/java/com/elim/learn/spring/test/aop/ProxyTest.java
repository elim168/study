/**
 * 
 */
package com.elim.learn.spring.test.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.junit.Test;

import com.elim.learn.spring.aop.service.IUserService;
import com.elim.learn.spring.aop.service.UserServiceImpl;

/**
 * 验证动态代理的用法，和内部调用不会走代理
 * @author Elim
 * 2017年4月22日
 */
public class ProxyTest {
	
	static IUserService PROXY_OBJ;//代理对象的引用
	
	@Test
	public void test() throws Exception {
		final IUserService target = new UserServiceImpl();//目标对象，真实的目标对象
		//代理对象，是对target对象的一个代理
		IUserService userService = (IUserService) Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class<?>[]{IUserService.class}, new InvocationHandler() {
			
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				//传入的proxy对象就是代理对象，即是上面的userService对象
				System.out.println(proxy == PROXY_OBJ);//true
				System.out.println("目标方法被调用前加入的逻辑");
				Object result = method.invoke(target, args);//调用真实对象的目标方法
				System.out.println("目标方法正常被被调用后加入的逻辑");
				return result;
			}
		});
		PROXY_OBJ = userService;
		userService.add();
	}
	
}
