/**
 * 
 */
package com.elim.learn.elastic.job.test;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Elim
 * 2016年10月29日
 */
public class ElasticJobTest {

	@Test
	public void test() throws Exception {
		ApplicationContext context = new ClassPathXmlApplicationContext("/applicationContext.xml");
		System.out.println(context);
		System.in.read();
	}
	
}
