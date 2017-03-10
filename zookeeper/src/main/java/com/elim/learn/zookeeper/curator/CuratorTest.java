/**
 * 
 */
package com.elim.learn.zookeeper.curator;

import java.util.concurrent.TimeUnit;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;

/**
 * @author Elim
 * 2017年3月6日
 */
public class CuratorTest {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		String connectString = "localhost:2181/elim";
		//重试策略，官方提供了好几种
		RetryPolicy retryPolicy = new RetryNTimes(5, 1000);
		CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient(connectString, retryPolicy);
		curatorFramework.start();
		//默认创建的是持久节点
		curatorFramework.create().forPath("/elim_test", "data".getBytes());
		
		
		curatorFramework.create()
			.creatingParentContainersIfNeeded()
			.withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
			.forPath("/a/b/c/d/e/f/g")
			;
		TimeUnit.SECONDS.sleep(100);
	}

}
