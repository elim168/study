/**
 * 
 */
package com.elim.learn.redis.basic;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.elim.learn.redis.pubsub.PubSubMessageHandler;
import com.elim.learn.redis.pubsub.listener.MyPubSubListener;
import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.RedisConnection;
import com.lambdaworks.redis.RedisFuture;
import com.lambdaworks.redis.pubsub.RedisPubSubConnection;
import com.lambdaworks.redis.pubsub.RedisPubSubListener;

/**
 * 发布订阅测试
 * @author elim
 *
 */
public class PubSubTest {

	private RedisClient client = null;
	private static final String CHANNEL = "Channel1";
	private static final String CHANNEL_2 = "Channel_2";
	private static final Logger logger = Logger.getLogger(PubSubTest.class);
	
	@Before
	public void before() {
		client = RedisClient.create("redis://localhost:6379");
	}
	
	@Test
	public void subscribe() throws Exception {
		RedisPubSubConnection<String, String> pubSubConn = client.connectPubSub();
		pubSubConn.addListener(new RedisPubSubListener<String, String>() {

			@Override
			public void message(String channel, String message) {
				logger.info(String.format("channel: %s, message: %s", channel, message));
			}

			@Override
			public void message(String pattern, String channel, String message) {
				logger.info(String.format("pattern: %s, channel: %s, message: %s", pattern, channel, message));
			}

			@Override
			public void subscribed(String channel, long count) {
				logger.info(String.format("channel: %s, count: %d", channel, count));
			}

			@Override
			public void psubscribed(String pattern, long count) {
				logger.info(String.format("pattern: %s, count: %d", pattern, count));
			}

			@Override
			public void unsubscribed(String channel, long count) {
				logger.info(String.format("channel: %s, count: %d", channel, count));
			}

			@Override
			public void punsubscribed(String pattern, long count) {
				logger.info(String.format("pattern: %s, count: %d", pattern, count));
			}
			
		});
		Long startSub = System.currentTimeMillis();
		//订阅指定的频道
		RedisFuture<Void> future = pubSubConn.subscribe(CHANNEL);
		future.get();
		Long endSub = System.currentTimeMillis();
		
		logger.info("complete subscribe in " + (endSub - startSub));
		
		TimeUnit.SECONDS.sleep(60);
	}
	
	@Test
	public void publish() {
		RedisConnection<String, String> connect = client.connect();
		for (int i=0; i<10; i++) {
			Long result = connect.publish(CHANNEL, String.format("This is message%d......", i));
			System.out.println(result);
		}
	}
	
	@Test
	public void getAllChannels() {
		RedisConnection<String, String> connect = client.connect();
		List<String> channels = connect.pubsubChannels();
		System.out.println(channels);
	}
	
	/**
	 * 使用自己的监听器，只处理自己监听的那个CHANNEL的信息
	 * @throws Exception
	 */
	@Test
	public void subcribe2() throws Exception {
		RedisPubSubConnection<String, String> pubSubConn = client.connectPubSub();
		new MyPubSubListener<String, String>(CHANNEL_2, new PubSubMessageHandler<String>() {

			@Override
			public void handle(String message) {
				//简单的输出一下这条信息
				logger.info("收到一条信息" + message);
			}
			
		}, pubSubConn);
		TimeUnit.SECONDS.sleep(60*60);
	}
	
	@Test
	public void publish2() {
		RedisConnection<String, String> connect = client.connect();
		connect.publish(CHANNEL_2, "now is :" + new Date());
	}
	
}
