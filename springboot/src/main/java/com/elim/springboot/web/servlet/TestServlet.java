package com.elim.springboot.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 使用内置的Servlet容器时，需要加上@ServletComponentScan才能扫描到@WebServlet/@WebFilter等。
 * @author Elim
 * 2018年7月17日
 */
@WebServlet("/servlet/test")
public class TestServlet extends HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = -2499437569852564816L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        writer.write("Hello Servlet.");
        writer.flush();
    }
    

}
