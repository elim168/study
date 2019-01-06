/**
 * 
 */
package com.elim.learn.spring.servlet3.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Elim
 * 2017年11月6日
 */
public class HelloServlet extends HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = -5646535135858594953L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        writer.println("hello world");
        writer.flush();
        writer.close();
    }

}
