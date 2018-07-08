/**
 * 
 */
package com.elim.learn.spring.mvc;

import java.nio.charset.Charset;
import java.util.List;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 验证通过@EnableWebMvc启用SpringMVC支持，该注解的作用类似于中XML文件中
 * 配置的&lt;mvc:annotation-config/>。不用配置文件，使用基于Java类的配置时需要指定DispatcherServlet的
 * contextClass为AnnotationConfigWebApplicationContext，指定contextConfigLocation为
 * 对应的Java配置类的全路径。
 * 
 * <pre>
 * &lt;servlet>
        &lt;servlet-name>springmvc&lt;/servlet-name>
        &lt;servlet-class>org.springframework.web.servlet.DispatcherServlet&lt;/servlet-class>
        &lt;init-param>
            &lt;param-name>contextConfigLocation&lt;/param-name>
            &lt;param-value>com.elim.learn.spring.mvc.MvcConfiguration&lt;/param-value>
        &lt;/init-param>
        &lt;init-param>
            &lt;param-name>contextClass&lt;/param-name>
            &lt;param-value>org.springframework.web.context.support.AnnotationConfigWebApplicationContext&lt;/param-value>
        &lt;/init-param>
        &lt;load-on-startup>1&lt;/load-on-startup>
    &lt;/servlet>
    &lt;servlet-mapping>
        &lt;servlet-name>springmvc&lt;/servlet-name>
        &lt;url-pattern>/&lt;/url-pattern>
    &lt;/servlet-mapping>
 * </pre>
 * 
 * 使用了@EnableWebMvc后会自动包含一些默认配置，如果需要自定义一些配置，可以通过实现WebMvcConfigurer接口或者
 * 继承WebMvcConfigurerAdapter覆写对应的方法。
 * 
 * @author Elim 2017年11月11日
 */
// @Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.elim.learn.spring.mvc.controller")
public class MvcConfiguration  extends WebMvcConfigurerAdapter  {

	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		registry.jsp("/WEB-INF/view/", ".jsp");
	}

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(new MappingJackson2HttpMessageConverter());
		converters.add(new StringHttpMessageConverter(Charset.defaultCharset()));
	}

}
