/**
 * 
 */
package com.yeelim.learn.spring.security;

import org.springframework.security.authentication.encoding.PasswordEncoder;

//import org.springframework.security.crypto.password.PasswordEncoder;


/**
 * @author Yeelim
 * @date 2014-6-8
 * @time 下午9:34:51 
 *
 */
public class MyPasswordEncoder implements PasswordEncoder {

	private String salt = "yeelim";
	
	public String encode(CharSequence rawPassword) {
		return rawPassword + salt;
	}

	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		return this.encode(rawPassword).equals(encodedPassword);
	}

	public String encodePassword(String rawPass, Object salt) {
		return null;
	}

	public boolean isPasswordValid(String encPass, String rawPass, Object salt) {
		return false;
	}

}
