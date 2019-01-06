/**
 * 
 */
package com.elim.learn.spring.mvc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.ModelAndView;

/**
 * 验证{@link org.springframework.web.servlet.handler.SimpleUrlHandlerMapping}
 * @author Elim
 * 2017年10月15日
 */
public class AbcController implements HandlerAdapter {

    private void test() {
        System.out.println("AAAAAAA");
    }

    @Override
    public boolean supports(Object handler) {
        return true;
    }

    @Override
    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        this.test();
        return null;
    }

    @Override
    public long getLastModified(HttpServletRequest request, Object handler) {
        return 0;
    }
    
}
