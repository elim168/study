package com.elim.learn.springdata.redis.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/applicationContext.xml")
public class BasicTest {

	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	
	@Test
	public void test() {
		String key = "testList";
		BoundListOperations<String, String> boundListOps = redisTemplate.boundListOps(key);
		Long result = boundListOps.rightPush("ABC");
		System.out.println(result);
	}
	
	@Test
	public void test2() {
		String key = "testList";
		BoundListOperations<String, String> boundListOps = redisTemplate.boundListOps(key);
		List<String> elements = boundListOps.range(0, boundListOps.size());
		System.out.println(elements);
	}
	
	@Test
	public void test3() {
		String key = "ABCDE";
		ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
		opsForValue.set(key, "ABCDEFG");
		String value = opsForValue.get(key);
		System.out.println(value);
	}
	
}
