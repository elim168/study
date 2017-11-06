/**
 * 
 */
package com.elim.learn.spring.servlet3;

import java.util.EnumSet;
import java.util.Set;

import javax.servlet.DispatcherType;
import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import com.elim.learn.spring.servlet3.filter.HelloFilter;
import com.elim.learn.spring.servlet3.listener.StartupListener;
import com.elim.learn.spring.servlet3.servlet.HelloServlet;

/**
 * Servlet3通过程序代码来注册Servlet/Listener/Filter的机制。需要通过SPI的机制在META-INF/services目录下
 * 创建javax.servlet.ServletContainerInitializer文件，并在其中填写ServletContainerInitializer的实现类，
 * 每个类写一行。
 * @author Elim
 * 2017年11月6日
 */
public class MyServletContainerInitializer implements ServletContainerInitializer {

    @Override
    public void onStartup(Set<Class<?>> c, ServletContext ctx) throws ServletException {
        //注册Servlet
        javax.servlet.ServletRegistration.Dynamic servletRegistration = ctx.addServlet("hello", HelloServlet.class);
        servletRegistration.addMapping("/servlet3/hello");
        servletRegistration.setLoadOnStartup(1);
        servletRegistration.setAsyncSupported(true);
        
        //注册监听器
        ctx.addListener(StartupListener.class);
        
        //注册Filter
        javax.servlet.FilterRegistration.Dynamic filterRegistration = ctx.addFilter("hello", HelloFilter.class);
        filterRegistration.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, "/servlet3/*");
    }

}
