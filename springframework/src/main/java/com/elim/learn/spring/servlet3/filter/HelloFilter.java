/**
 * 
 */
package com.elim.learn.spring.servlet3.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 验证通过程序注册Filter
 * @author Elim
 * 2017年11月6日
 */
public class HelloFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(HelloFilter.class);
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("初始化HelloFilter……");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        logger.info("HelloFilter拦截到请求-" + req.getRequestURI());
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }

}
