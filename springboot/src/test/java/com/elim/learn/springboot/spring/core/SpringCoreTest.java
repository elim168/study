package com.elim.learn.springboot.spring.core;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DefaultDataBuffer;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
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
	
	/**
	 * 没有验出效果
	 */
	@Test
	public void testNullable() {
		String nonNullStr = null;
		this.nullableService.nonNull(nonNullStr);
	}
	
	@Test
	public void testDataBuffer() {
		DefaultDataBufferFactory dataBufferFactory = new DefaultDataBufferFactory();
		DefaultDataBuffer dataBuffer = dataBufferFactory.allocateBuffer();
		dataBuffer.write("abc".getBytes());
		int readableByteCount = dataBuffer.readableByteCount();
		Assert.assertEquals(3, readableByteCount);
		byte[] destination = new byte[readableByteCount];
		dataBuffer.read(destination);
		String result = new String(destination, 0, readableByteCount);
		Assert.assertEquals("abc", result);
	}
	
}
