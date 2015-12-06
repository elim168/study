/**
 * 
 */
package com.yeelim.learn.spring.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

/**
 * @author Yeelim
 * @date 2014-6-7
 * @time 下午11:15:17 
 *
 */
public class AuthenticationFailureHandlerImpl implements
		AuthenticationFailureHandler {

	public void onAuthenticationFailure(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException exception)
			throws IOException, ServletException {
		response.sendRedirect(request.getContextPath());
	}

}
