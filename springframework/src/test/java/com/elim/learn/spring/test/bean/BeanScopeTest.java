/**
 * 
 */
package com.elim.learn.spring.test.bean;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.elim.learn.spring.bean.BeanA;
import com.elim.learn.spring.bean.BeanB;

/**
 * @author Elim
 * 2017年7月20日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-bean.xml")
public class BeanScopeTest {

	@Autowired
	private BeanA beanA;
	
	@Test
	public void test() {
		BeanB beanB = beanA.getBeanB();
		System.out.println(beanB);
		System.out.println(beanB.getClass());
		Object a = beanB.getA();
		
		BeanB beanB2 = beanA.getBeanB();
		Object a2 = beanB2.getA();
		System.out.println(beanB == beanB2);
		System.out.println(a == a2);
	}
	
	@Test
	public void test08_1() {
		Object user = new Object() {
			@SuppressWarnings("unused")
			public List<String> getInterests() {
				List<String> interests = Arrays.asList(new String[] {"BasketBall", "FootBall"});
				return interests;
			}
		};
		ExpressionParser parser = new SpelExpressionParser();
		Assert.assertTrue(parser.parseExpression("interests[0]").getValue(user, String.class).equals("BasketBall"));
		Assert.assertTrue(parser.parseExpression("interests[1]").getValue(user, String.class).equals("FootBall"));
	}
		
	@Test
	public void test08_2() {
		Object user = new Object() {
			@SuppressWarnings("unused")
			public String[] getInterests() {
				return new String[] {"BasketBall", "FootBall"};
			}
		};
		ExpressionParser parser = new SpelExpressionParser();
		Assert.assertTrue(parser.parseExpression("interests[0]").getValue(user, String.class).equals("BasketBall"));
		Assert.assertTrue(parser.parseExpression("interests[1]").getValue(user, String.class).equals("FootBall"));
	}
	
}
