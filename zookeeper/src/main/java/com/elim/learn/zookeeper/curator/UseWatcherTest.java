/**
 * 
 */
package com.elim.learn.zookeeper.curator;

import java.util.concurrent.TimeUnit;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Elim
 * 2017年3月13日
 */
public class UseWatcherTest {

	private static final Logger logger = LoggerFactory.getLogger(UseWatcherTest.class);
	
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {

		String connectString = "localhost:2181/elim";
		//重试策略，官方提供了好几种
		RetryPolicy retryPolicy = new RetryNTimes(5, 1000);
		final CuratorFramework client = CuratorFrameworkFactory.newClient(connectString, retryPolicy);
		client.start();
		Watcher watcher = new Watcher() {

			@Override
			public void process(WatchedEvent event) {
				logger.info("接收到事件[{}]，作用路径是{}", event.getType(), event.getPath());
				try {
					client.checkExists().usingWatcher(this).forPath("/test2");
				} catch (Exception e) {
					logger.error("ZooKeeper异常", e);
				}
			}
			
		};
		client.checkExists().usingWatcher(watcher).forPath("/test2");
		
		
		final NodeCache nodeCache = new NodeCache(client, "/test2");
		nodeCache.getListenable().addListener(new NodeCacheListener() {

			@Override
			public void nodeChanged() throws Exception {
				logger.info("nodeCache接收到监听的节点路径事件，路径是{}", nodeCache.getCurrentData().getPath());
			}
			
		});
		nodeCache.start();
		TimeUnit.HOURS.sleep(1);
		nodeCache.close();
	}

}
