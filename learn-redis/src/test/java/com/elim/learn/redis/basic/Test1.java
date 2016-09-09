package com.elim.learn.redis.basic;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.RedisConnection;
import com.lambdaworks.redis.RedisStringsConnection;

/**
 * 基础测试
 * @author elim
 *
 */
public class Test1 {
	
	private RedisClient client = null;
	
	@Before
	public void before() {
		client = RedisClient.create("redis://localhost:6379");
	}

	@Test
	public void test1() {
		RedisClient client = RedisClient.create("redis://localhost:6379");
		RedisStringsConnection<String, String> connection = client.connect();
		String value = connection.get("abc");
		System.out.println(value);
	}
	
	@Test
	public void test2() {
		RedisConnection<String, String> connect = client.connect();
		Map<String, String> map = new HashMap<>();
		map.put("id", "1");
		map.put("username", "zhangsan");
		map.put("password", "123");
		map.put("realName", "张三");
		String result = connect.hmset("user:1", map);
		System.out.println(result);
	}
	
	@Test
	public void test3() {
		RedisConnection<String, String> connect = client.connect(); 
		Map<String, String> user1 = connect.hgetall("user:1");
		System.out.println(user1);
	}
	
	@Test
	public void test4() {
		RedisConnection<String, String> connect = client.connect();
		Set<String> members = connect.smembers("set");
		System.out.println(members);
	}
	
}
