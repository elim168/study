package com.elim.springboot.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component("hello2")
@WebServlet("/servlet_hello2")
public class HelloServlet2 extends HttpServlet {

    @Autowired
    private ApplicationContext applicationContext;
    
    /**
     * 
     */
    private static final long serialVersionUID = 8345578389259773375L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        writer.println("bean names: ");
        for (String name : this.applicationContext.getBeanDefinitionNames()) {
            writer.println(name);
        }
        writer.flush();
    }

}
