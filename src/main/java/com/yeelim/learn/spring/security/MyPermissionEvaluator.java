/**
 * 
 */
package com.yeelim.learn.spring.security;

import java.io.Serializable;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

/**
 * @author Yeelim
 * @date 2014-6-25
 * @time 下午11:33:01 
 *
 */
public class MyPermissionEvaluator implements PermissionEvaluator {

	/* (non-Javadoc)
	 * @see org.springframework.security.access.PermissionEvaluator#hasPermission(org.springframework.security.core.Authentication, java.lang.Object, java.lang.Object)
	 */
	public boolean hasPermission(Authentication authentication,
			Object targetDomainObject, Object permission) {
		System.out.println(targetDomainObject);
		return false;
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.access.PermissionEvaluator#hasPermission(org.springframework.security.core.Authentication, java.io.Serializable, java.lang.String, java.lang.Object)
	 */
	public boolean hasPermission(Authentication authentication,
			Serializable targetId, String targetType, Object permission) {
		return false;
	}

}
