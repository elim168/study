/**
 * 
 */
package com.elim.learn.redis.pubsub.listener;

import com.elim.learn.redis.pubsub.PubSubMessageHandler;
import com.lambdaworks.redis.pubsub.RedisPubSubAdapter;
import com.lambdaworks.redis.pubsub.RedisPubSubConnection;

/**
 * 实现自己的监听器有两种方式，一种是直接实现RedisPubSubListener接口，另一种就是直接继续
 * RedisPubSubAdapter，然后只重写自己感兴趣的方法
 * @author elim
 *
 */
public class MyPubSubListener<K, V> extends RedisPubSubAdapter<K, V> {

	private K channel;
	private PubSubMessageHandler<V> messageHandler;

	@SuppressWarnings("unchecked")
	public MyPubSubListener(K channel, PubSubMessageHandler<V> messageHandler, RedisPubSubConnection<K, V> pubSubConn) {
		this.channel = channel;
		this.messageHandler = messageHandler;
		pubSubConn.addListener(this);
		pubSubConn.subscribe(channel);
	}

	/* (non-Javadoc)
	 * @see com.lambdaworks.redis.pubsub.RedisPubSubAdapter#message(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void message(K channel, V message) {
		if (channel.equals(this.channel)) {//只处理自己订阅的频道的信息
			this.messageHandler.handle(message);
		}
	}
	
}
