/**
 * 
 */
package com.elim.learn.redis.pubsub;

/**
 * 发布订阅的消息处理器
 * @author elim
 *
 */
public interface PubSubMessageHandler<T> {

	/**
	 * 对订阅的消息进行处理的回调方法
	 * @param message
	 */
	public void handle(T message);
	
}
