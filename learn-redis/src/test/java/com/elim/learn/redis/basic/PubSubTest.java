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

import com.elim.learn.redis.pubsub.listener.MyPubSubListener;
import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.RedisConnection;
import com.lambdaworks.redis.RedisFuture;
import com.lambdaworks.redis.pubsub.RedisPubSubAdapter;
import com.lambdaworks.redis.pubsub.RedisPubSubConnection;
import com.lambdaworks.redis.pubsub.RedisPubSubListener;

/**
 * 发布订阅测试
 * @author elim
 *
 */
public class PubSubTest {

	private static final RedisClient CLIENT = RedisClient.create("redis://localhost:6379");;
	private static final String CHANNEL = "Channel1";
	private static final String CHANNEL_2 = "Channel_2";
	private static final Logger logger = Logger.getLogger(PubSubTest.class);
	
	private RedisPubSubConnection<String, String> pubSubConn = null;
	
	@Before
	public void before() {
		pubSubConn = CLIENT.connectPubSub();
	}
	
	@Test
	public void subscribe() throws Exception {
		pubSubConn.addListener(new RedisPubSubListener<String, String>() {

			@Override
			public void message(String channel, String message) {
				//直接通过channel订阅的收到信息时将回调此方法
				logger.info(String.format("channel: %s, message: %s", channel, message));
			}

			@Override
			public void message(String pattern, String channel, String message) {
				//通过pattern订阅的相应channel收到信息时将回调此方法
				logger.info(String.format("pattern: %s, channel: %s, message: %s", pattern, channel, message));
			}

			@Override
			public void subscribed(String channel, long count) {
				//通过channel进行了消息订阅时将回调此方法
				logger.info(String.format("channel: %s, count: %d", channel, count));
			}

			@Override
			public void psubscribed(String pattern, long count) {
				//通过pattern进行了频道订阅时将回调此方法
				logger.info(String.format("pattern: %s, count: %d", pattern, count));
			}

			@Override
			public void unsubscribed(String channel, long count) {
				//通过channel进行取消订阅后将回调此方法
				logger.info(String.format("channel: %s, count: %d", channel, count));
			}

			@Override
			public void punsubscribed(String pattern, long count) {
				//经过pattern进行了频道的取消订阅后将回调此方法
				logger.info(String.format("pattern: %s, count: %d", pattern, count));
			}
			
		});
		Long startSub = System.currentTimeMillis();
		//订阅指定的频道
		RedisFuture<Void> future = pubSubConn.subscribe(CHANNEL);
		future.get();
		Long endSub = System.currentTimeMillis();
		
		logger.info("complete subscribe in " + (endSub - startSub));
		
		TimeUnit.SECONDS.sleep(60 * 10);
	}
	
	@Test
	public void publish() {
		RedisConnection<String, String> connect = CLIENT.connect();
		for (int i=0; i<10; i++) {
			Long result = connect.publish(CHANNEL, String.format("This is message%d......", i));
			System.out.println(result);
		}
	}
	
	@Test
	public void getAllChannels() {
		RedisConnection<String, String> connect = CLIENT.connect();
		List<String> channels = connect.pubsubChannels();
		System.out.println(channels);
	}
	
	/**
	 * 使用自己的监听器，只处理自己监听的那个CHANNEL的信息
	 * @throws Exception
	 */
	@Test
	public void subcribe2() throws Exception {
		RedisPubSubListener<String, String> myPubSubListener = new MyPubSubListener<String, String>();
		pubSubConn.addListener(myPubSubListener);
		pubSubConn.subscribe(CHANNEL_2);
		TimeUnit.SECONDS.sleep(60*60);
	}
	
	@Test
	public void publish2() {
		RedisConnection<String, String> connect = CLIENT.connect();
		connect.publish(CHANNEL_2, "now is :" + new Date());
	}
	
	/**
	 * 
	 * @throws Exception
	 */
	@Test
	public void subscribe3() throws Exception {
		RedisFuture<Void> future = pubSubConn.psubscribe("Channel1*");
		future.get();
		pubSubConn.addListener(new RedisPubSubAdapter<String, String>() {

			/* (non-Javadoc)
			 * @see com.lambdaworks.redis.pubsub.RedisPubSubAdapter#message(java.lang.Object, java.lang.Object, java.lang.Object)
			 */
			@Override
			public void message(String pattern, String channel, String message) {
				//用pattern进行的订阅将回调此方法
				logger.info(String.format("收到一条消息，pattern: %s, channel: %s, message: %s", pattern, channel, message));
			}
			
		});
		TimeUnit.MINUTES.sleep(30);
	}
	
	@Test
	public void subscribe4() throws Exception {
		RedisFuture<Void> future = pubSubConn.subscribe(CHANNEL);
		future.get();
		pubSubConn.addListener(new RedisPubSubAdapter<String, String>() {

			/* (non-Javadoc)
			 * @see com.lambdaworks.redis.pubsub.RedisPubSubAdapter#message(java.lang.Object, java.lang.Object)
			 */
			@Override
			public void message(String channel, String message) {
				//直接通过channel订阅的将回调此方法
				logger.info(String.format("收到一条信息，channel: %s, message: %s", channel, message));
			}
			
		});
		TimeUnit.MINUTES.sleep(30);
	}
	
}
