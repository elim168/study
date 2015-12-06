/**
 * 
 */
package com.yeelim.learn.spring.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * @author Yeelim
 * @date 2014-6-7
 * @time 下午10:25:48 
 *
 */
public class AuthenticationSuccessHandlerImpl implements
		AuthenticationSuccessHandler {

	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		System.out.println("=========-=-=-=-=-=-=-=------=-=--=-----");
		response.sendRedirect(request.getContextPath());
	}

}
