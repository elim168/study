package com.elim.learn.springdata.redis.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.elim.learn.springdata.redis.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/applicationContext.xml")
public class BasicTest {

	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	
	/**
	 * 简单测试
	 */
	@Test
	public void test() {
		String key = "testList";
		//BoundListOperations是绑定于指定的Key后的一个接口，其所有操作都是基于指定的Key的，在操作时不再需要指定Key
		BoundListOperations<String, String> boundListOps = redisTemplate.boundListOps(key);
		Long result = boundListOps.rightPush("ABC");
		System.out.println(result);
		//ListOperations是基于List的操作，其所有的操作都需要指定作用的Key。
		ListOperations<String, String> opsForList = redisTemplate.opsForList();
		opsForList.leftPush(key, "DEF");
	}
	
	/**
	 * 简单的测试获取List的元素
	 */
	@Test
	public void test2() {
		String key = "testList";
		BoundListOperations<String, String> boundListOps = redisTemplate.boundListOps(key);
		List<String> elements = boundListOps.range(0, boundListOps.size());
		System.out.println(elements);
	}
	
	/**
	 * 简单的测试ValueOperation
	 */
	@Test
	public void test3() {
		String key = "ABCDE";
		ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
		opsForValue.set(key, "ABCDEFG");
		String value = opsForValue.get(key);
		System.out.println(value);
	}
	
	/**
	 * 简单的测试pipelined
	 */
	@Test
	public void test4() {
		List<Object> results = redisTemplate.executePipelined(new RedisCallback<String>() {

			@SuppressWarnings("unchecked")
			@Override
			public String doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> valueSerializer = (RedisSerializer<String>) redisTemplate.getValueSerializer();
				byte[] key1 = redisTemplate.getStringSerializer().serialize("abc");
				byte[] key2 = redisTemplate.getStringSerializer().serialize("def");
				byte[] value1 = valueSerializer.serialize("ABC");
				byte[] value2 = valueSerializer.serialize("DEF");
				
				connection.set(key1, value1);
				connection.set(key2, value2);
				connection.get(key1);
				connection.get(key2);
				
				return null;
			}
			
		});
		System.out.println("--------f");
		System.out.println(results);
		System.out.println("--------f");
	}
	
	/**
	 * 简单的基于Hash的测试
	 */
	@Test
	public void test5() {
		String key = "user:1";
		BoundHashOperations<String, Object, Object> boundHashOps = redisTemplate.boundHashOps(key);
		User user = new User(1L, "张三", 30);
		Map<Object, Object> userMap = new HashMap<Object, Object>();
		userMap.put("id", user.getId());
		userMap.put("name", user.getName());
		userMap.put("age", user.getAge());
		boundHashOps.putAll(userMap);
		
		Map<Object, Object> entries = boundHashOps.entries();
		System.out.println(entries);
	}
	
}
