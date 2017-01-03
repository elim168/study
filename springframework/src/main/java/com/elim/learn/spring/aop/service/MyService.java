/**
 * 
 */
package com.elim.learn.spring.aop.service;

import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Service;

/**
 * @author Elim
 * 2016年12月30日
 */
@Service
public class MyService {

	@Pointcut("execution(* add(..))")
	public void add() {
		System.out.println("add……");
	}
	
}
