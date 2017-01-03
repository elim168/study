/**
 * 
 */
package com.elim.learn.spring.aop.service;

import org.springframework.stereotype.Service;

/**
 * @author Elim
 * 2017年1月3日
 */
@Service("subUserService")
public class SubUserServiceImpl extends UserServiceImpl {

	/* (non-Javadoc)
	 * @see com.elim.learn.spring.aop.service.UserServiceImpl#add()
	 */
	@Override
	public void add() {
		System.out.println("-----------sub service add user--------------");
	}
	
	public void test() {
		System.out.println("------------sub service test----------------");
	}

}
