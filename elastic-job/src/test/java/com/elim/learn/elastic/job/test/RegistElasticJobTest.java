/**
 * 
 */
package com.elim.learn.elastic.job.test;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 验证动态的注册Job的情形
 * 
 * @author Elim 2017年11月8日
 */
public class RegistElasticJobTest {

    @Test
    public void test() throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("/applicationContext-register.xml");
        System.out.println(context);
        System.in.read();
    }

}
