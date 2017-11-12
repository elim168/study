/**
 * 
 */
package com.elim.learn.spring.mvc;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.web.WebApplicationInitializer;

/**
 * Spring提供了实现了Servlet3的ServletContainerInitializer接口的SpringServletContainerInitializer，
 * 其会在类路径下寻找WebApplicationInitializer接口实现，用于注册Servlet/Filter等Http对象。
 * 所以对于DispatcherServlet我们也可以不在web.xml中进行定义。而改为实现WebApplicationInitializer，通过
 * 接口方法中的ServletContext参数进行注册。Spring也提供了WebApplicationInitializer的一个抽象实现，
 * AbstractDispatcherServletInitializer，通过继承该抽象类，我们可以通过重写其中的方法的方式处理我们关心的某部分内容，
 * 比如可以通过重写getServletMappings方法指定servlet的映射路径;通过重写getServletFilters方法指定DispatcherServlet需要
 * 应用的Filter;也可以通过重写customizeRegistration自定义一些配置。
 * @author Elim
 * 2017年11月8日
 */
public class MyWebApplicationInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        /*XmlWebApplicationContext appContext = new XmlWebApplicationContext();
        appContext.setConfigLocation("/WEB-INF/applicationContext-mvc.xml");
        ServletRegistration.Dynamic registration = servletContext.addServlet("dispatcher", new
        DispatcherServlet(appContext));
        registration.setLoadOnStartup(1);
        registration.setAsyncSupported(true);
        registration.addMapping("/springmvc/*");*/
    }

}
