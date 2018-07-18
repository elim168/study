package com.elim.springboot.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 官网说使用内置的Servlet容器时，在bean中定义的Servlet、Filter等会被自动注册到Web容器中，请求路径就是bean名称。该servlet的请求路径是/servlet_bean/
 * @author Elim
 * 2018年7月17日
 */
@Component("servlet_bean")
@Order(Integer.MIN_VALUE+1)
public class BeanServlet extends HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = -8716198321234200964L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        writer.write("hello bean servlet.");
        writer.flush();
    }
    

}
