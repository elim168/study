/**
 * 
 */
package com.elim.learn.spring.test.aop;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.elim.learn.spring.aop.aspect.WithinTestAspect;
import com.elim.learn.spring.bean.BeanA;
import com.elim.learn.spring.bean.BeanB;
import com.elim.learn.spring.bean.BeanC;
import com.elim.learn.spring.bean.WithinTestConfiguration;

/**
 * @author Elim
 * 2017年9月14日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={WithinTestConfiguration.class, WithinTestAspect.class})
public class WithinTest {

	@Autowired
	private BeanA beanA;
	@Autowired
	private BeanB beanB;
	@Autowired
	private BeanC beanC;
	
	@Test
	public void test() {
		beanA.getBeanB().getA();
		beanB.getA();
		System.out.println("-=-==-=--=-=-=-=-=-=-=-=-=-=-=-=-=-==--=-=-=-==-");
		beanC.cmethod();
		beanC.getBeanB();
	}
	
}
