/**
 * 
 */
package com.elim.learn.spring.servlet3;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import com.elim.learn.spring.servlet3.servlet.HelloServlet;

/**
 * 实现@HandlesTypes指定的接口
 * @author Elim
 * 2017年11月7日
 */
public class MyWebInitializer implements WebInitializer {

    @Override
    public void onStartup(ServletContext ctx) throws ServletException {
        javax.servlet.ServletRegistration.Dynamic servletRegistration = ctx.addServlet("helloServlet", HelloServlet.class);
        servletRegistration.addMapping("/servlet3/hello2");
    }

}
