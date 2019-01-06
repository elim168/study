/**
 * 
 */
package com.elim.learn.dubbo.test;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.elim.learn.dubbo.service.UserService;

/**
 * @author Elim
 * 2016年10月11日
 *
 */
public class BasicTest {

	private static final Logger LOGGER = Logger.getLogger(BasicTest.class);
	
	@Test
	public void testServer() throws IOException {
		ApplicationContext context = new ClassPathXmlApplicationContext("application-server.xml");
		System.out.println(context.getBeanDefinitionCount());
		System.in.read();
	}
	
	@Test
	public void testClient() {
		ApplicationContext context = new ClassPathXmlApplicationContext("application-client.xml");
		UserService userService = (UserService) context.getBean("userService");
		userService.sayHello("张三");
	}
	
	@Test
	public void testCache() {
		ApplicationContext context = new ClassPathXmlApplicationContext("application-client.xml");
		UserService userService = (UserService) context.getBean("userService");
		String fix = null;
        for (int i = 0; i < 5; i ++) {
        	//这里会依次缓存参数为0-4对应的结果，所以刚开始的时候每次结果都会不一样的，因为每次都没有缓存命中
            String result = userService.cacheTest(i);
            if (fix == null || fix.equals(result)) {
                LOGGER.info("OK: " + result);
            } else {
                LOGGER.error("ERROR: " + result);
            }
            fix = result;
        }
        // LRU的缺省cache.size为1000，执行1001次，应有溢出
        for (int n = 0; n < 1001; n ++) {
            String pre = null;
            for (int i = 0; i < 10; i ++) {
                String result = userService.cacheTest(n);//当i为0的时候会缓存其中的结果，之后都是从缓存中获取
                //这里面永远都不会为真，因为每次遍历的时候都缓存了第一次调用时传递的参数对应的结果
                if (pre != null && ! pre.equals(result)) {
                    LOGGER.error("ERROR: " + result);
                }
                pre = result;
                LOGGER.info("pre: " + pre);
            }
        }
        // 测试LRU有移除最开始的一个缓存项
        String result = userService.cacheTest(0);//因为LRU只缓存1000个项，所以这里再次调用的时候对应的缓存已经清除了，又会重新请求服务
        if (fix != null && ! fix.equals(result)) {
            LOGGER.info("OK: " + result);
        } else {
            LOGGER.error("ERROR: " + result);
        }
	}
	
}
