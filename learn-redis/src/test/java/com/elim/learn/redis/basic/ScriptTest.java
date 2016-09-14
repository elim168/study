/**
 * 
 */
package com.elim.learn.redis.basic;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.RedisConnection;
import com.lambdaworks.redis.ScriptOutputType;

/**
 * 
 * Redis也支持脚本，是基于LUA的脚本
 * @author elim
 *
 */
public class ScriptTest {

	private static final RedisClient CLIENT = RedisClient.create("redis://localhost:6379");
	private RedisConnection<String, String> connect = null;
	
	@Before
	public void before() {
		connect = CLIENT.connect();
	}
	
	@Test
	public void test1() {
		String script = "return redis.call('smembers', KEYS[1])";
		List<String> obj = connect.eval(script, ScriptOutputType.MULTI, "set");
		System.out.println(obj);
	}
	
}
