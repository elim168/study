/**
 * 
 */
package com.yeelim.learn.spring.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author Yeelim
 * @date 2014-6-15
 * @time 上午9:57:54 
 *
 */
public class CustomFilter implements Filter {

	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("--=======================================init");
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		System.out.println("=====================doFilter");
		chain.doFilter(request, response);
	}

	public void destroy() {
		System.out.println("===================================destroy");
	}

}
