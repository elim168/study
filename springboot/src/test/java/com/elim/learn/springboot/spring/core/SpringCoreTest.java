package com.elim.learn.springboot.spring.core;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 
 * @author Elim
 * 2018年5月3日
 */
@ContextConfiguration(classes=SpringCoreConfiguration.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class SpringCoreTest {

	@Autowired
	private NullableService nullableService;
	
	@Test
	public void testNullable() {
		String nonNullStr = null;
		this.nullableService.nonNull(nonNullStr);
	}
	
}
