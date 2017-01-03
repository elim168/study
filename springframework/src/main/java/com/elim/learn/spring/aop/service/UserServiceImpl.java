/**
 * 
 */
package com.elim.learn.spring.aop.service;

import org.springframework.stereotype.Service;

import com.elim.learn.spring.support.ArgWithAnnotation;
import com.elim.learn.spring.support.MyAnnotation;

/**
 * @author Elim
 * 2017年1月2日
 */
@Service("userService")
@MyAnnotation
public class UserServiceImpl implements IUserService {

	/* (non-Javadoc)
	 * @see com.elim.learn.spring.aop.service.IUserService#add()
	 */
	@Override
	@MyAnnotation
	public void add() {
		System.out.println("--------add user----------");
	}

	/* (non-Javadoc)
	 * @see com.elim.learn.spring.aop.service.IUserService#add(int, java.lang.String)
	 */
	@Override
	public void add(@MyAnnotation int orgId, String name) {
		System.out.println("---------add user with 2 args(Integer, String)-------------");
	}

	/* (non-Javadoc)
	 * @see com.elim.learn.spring.aop.service.IUserService#add(com.elim.learn.spring.support.ArgWithAnnotation)
	 */
	@Override
	public void add(ArgWithAnnotation arg) {
		System.out.println("--------add 方法参数类型上是有注解的----------");
	}

}
