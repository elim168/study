/**
 * 
 */
package com.elim.learn.redis.pubsub.listener;

import org.apache.log4j.Logger;

import com.lambdaworks.redis.pubsub.RedisPubSubAdapter;

/**
 * 实现自己的监听器有两种方式，一种是直接实现RedisPubSubListener接口，另一种就是直接继续
 * RedisPubSubAdapter，然后只重写自己感兴趣的方法
 * @author elim
 *
 */
public class MyPubSubListener<K, V> extends RedisPubSubAdapter<K, V> {

	private static final Logger logger = Logger.getLogger(MyPubSubListener.class);

	/* (non-Javadoc)
	 * @see com.lambdaworks.redis.pubsub.RedisPubSubAdapter#message(java.lang.Object, java.lang.Object, java.lang.Object)
	 */
	@Override
	public void message(K pattern, K channel, V message) {
		logger.info(String.format("收到了通过pattern订阅的频道信息， channel: %s, message: %s", channel, message));
	}


	/* (non-Javadoc)
	 * @see com.lambdaworks.redis.pubsub.RedisPubSubAdapter#message(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void message(K channel, V message) {
		logger.info(String.format("收到了通过channel订阅的频道信息， channel: %s, message: %s", channel, message));
	}
	
}
