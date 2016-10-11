/**
 * 
 */
package com.elim.learn.dubbo.service;

/**
 * @author Elim
 * 2016年10月11日
 *
 */
public class UserServiceImpl implements UserService {

	/* (non-Javadoc)
	 * @see com.elim.learn.dubbo.service.UserService#sayHello(java.lang.String)
	 */
	@Override
	public void sayHello(String name) {
		System.out.println("Hello " + name);
	}

}
