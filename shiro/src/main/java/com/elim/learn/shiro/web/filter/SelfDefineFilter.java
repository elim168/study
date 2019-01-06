package com.elim.learn.shiro.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

public class SelfDefineFilter implements Filter {

	private static final Logger logger = Logger.getLogger(SelfDefineFilter.class);
	
	@Override
	public void destroy() {
		logger.info("destory-------------");
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		logger.info("处理了一个请求——" + request.getRequestURI());
		chain.doFilter(request, resp);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		logger.info("init-------------");
	}

}
