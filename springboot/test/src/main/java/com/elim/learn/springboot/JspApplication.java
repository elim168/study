package com.elim.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * 使用JSP的应用
 * @author Elim
 * 19-1-16
 */
@SpringBootApplication
public class JspApplication extends SpringBootServletInitializer {

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
    return builder.sources(JspApplication.class);
  }

  public static void main(String[] args) {
    SpringApplication.run(JspApplication.class, args);
  }

}
