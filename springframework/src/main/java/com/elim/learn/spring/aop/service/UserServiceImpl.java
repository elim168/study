/**
 * 
 */
package com.elim.learn.spring.aop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elim.learn.spring.common.model.User;
import com.elim.learn.spring.support.ArgWithAnnotation;
import com.elim.learn.spring.support.MyAnnotation;

/**
 * @author Elim
 * 2017年1月2日
 */
@Service("userService")
@MyAnnotation
public class UserServiceImpl implements IUserService {
	
	@Autowired
	private IUserService self;//不能自己注入自己，这里注入的是它自己的子类

	/* (non-Javadoc)
	 * @see com.elim.learn.spring.aop.service.IUserService#add()
	 */
	@Override
	@MyAnnotation
	public void add() throws Exception {
		System.out.println("--------add user----------");
		System.out.println("--------self class is : " + self.getClass());
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

	/* (non-Javadoc)
	 * @see com.elim.learn.spring.aop.service.IUserService#findById(java.lang.Integer)
	 */
	@Override
	public User findById(Integer id) {
		if (id == null) {
			throw new IllegalArgumentException("[id] must not be null.");
		}
		User user = new User();
		user.setId(id);
		user.setName("Name-" + id);
		return user;
	}

	/* (non-Javadoc)
	 * @see com.elim.learn.spring.aop.service.IUserService#delete(java.lang.String, int)
	 */
	@Override
	@MyAnnotation
	public void delete(String name, int sex) {
		System.out.println("--------deleting with param[name=" + name + ", sex=" + sex + "]");
	}

	/* (non-Javadoc)
	 * @see com.elim.learn.spring.aop.service.IUserService#testParam(java.lang.Object)
	 */
	@Override
	public <T> void testParam(T param) {
		System.out.println("================传递的param是=======" + param);
	}

	/* (non-Javadoc)
	 * @see com.elim.learn.spring.aop.service.IUserService#throwException(java.lang.Exception)
	 */
	@Override
	public void throwException(Exception e) throws Exception {
		throw e;
	}

}
