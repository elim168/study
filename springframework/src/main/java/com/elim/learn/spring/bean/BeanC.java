/**
 * 
 */
package com.elim.learn.spring.bean;

/**
 * @author Elim
 * 2017年9月14日
 */
public class BeanC extends BeanA {

	public void cmethod() {
		System.out.println("CMethod............");
	}

	@Override
	public BeanB getBeanB() {
		//但是如果子类ClassB覆写了父类ClassA的a()方法，则调用ClassB.a()方法时也不匹配该Pointcut。
		return new BeanB();
	}
	
}
