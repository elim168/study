/**
 * 
 */
package com.elim.learn.spring.servlet3;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 * 自定义的Web初始化接口
 * @author Elim
 * 2017年11月6日
 */
public interface WebInitializer {

    void onStartup(ServletContext ctx) throws ServletException; 
    
}
