/**
 * 
 */
package com.elim.learn.spring.test.aop;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.elim.learn.spring.aop.service.ProxyFactoryBeanTestService;

/**
 * @author Elim
 * 2017年5月10日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class ProxyFactoryBeanTest {

	@Autowired
	private ProxyFactoryBeanTestService proxyFactoryBeanTestService;
	
	@Test
	public void test() {
		System.out.println(this.proxyFactoryBeanTestService.getClass());
		this.proxyFactoryBeanTestService.test();
	}
	
}
