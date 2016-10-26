/**
 * 
 */
package com.yeelim.learn.spring.security;

import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Yeelim
 * @date 2014-6-8
 * @time 下午8:49:07 
 *
 */
public class MySaltSource implements SaltSource {

	public Object getSalt(UserDetails user) {
		return null;
	}

}
