/**
 * 
 */
package com.elim.learn.spring.servlet3.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 启动监听器
 * @author Elim
 * 2017年11月6日
 */
public class StartupListener implements ServletContextListener {

    private static final Logger logger = LoggerFactory.getLogger(StartupListener.class);
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("服务启动了");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("服务正准备关闭了");
    }

}
