/**
 * 
 */
package com.yeelim.learn.spring.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.web.filter.GenericFilterBean;

/**
 * @author Yeelim
 * @date 2014-6-15
 * @time 上午10:06:04 
 *
 */
public class CustomFilter2 extends GenericFilterBean {

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		System.out.println("=========GenericFilterBean======doFilter");
		chain.doFilter(request, response);
	}

	@Override
	protected void initFilterBean() throws ServletException {
		System.out.println("==============GenericFilterBean======initFilterBean");
		System.out.println(this.getFilterConfig() + "============" + this.getFilterName());
	}

	@Override
	public void destroy() {
		System.out.println("================GenericFilterBean=========destroy");
	}
	
}
