/**
 * 
 */
package com.elim.learn.redis.util;

import com.lambdaworks.redis.RedisClient;

/**
 * 
 * Redis工具类
 * @author elim
 *
 */
public class RedisUtils {

	private static final RedisClient CLIENT = RedisClient.create("redis://localhost:6379");
	
	/**
	 * 获取一个客户端
	 * @return
	 */
	public static RedisClient getClient() {
		return CLIENT;
	}
	
}
