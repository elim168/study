/**
 * 
 */
package com.elim.learn.dubbo.service;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Elim
 * 2016年10月11日
 *
 */
public class UserServiceImpl implements UserService {

	private AtomicInteger counter = new AtomicInteger(0);
	
	/* (non-Javadoc)
	 * @see com.elim.learn.dubbo.service.UserService#sayHello(java.lang.String)
	 */
	@Override
	public void sayHello(String name) {
		System.out.println("Hello " + name);
		try {
			TimeUnit.MILLISECONDS.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see com.elim.learn.dubbo.service.UserService#cacheTest(int)
	 */
	@Override
	public String cacheTest(int id) {
		return "id: " + id + ", counter: " + counter.incrementAndGet();
	}

}
