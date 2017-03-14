/**
 * 
 */
package com.elim.learn.zookeeper.curator;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.barriers.DistributedDoubleBarrier;
import org.apache.curator.retry.RetryNTimes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 屏障的进入和离开都是阻塞型的，都需要达到指定数量的线程数
 * @author Elim
 * 2017年3月14日
 */
public class DistributedDoubleBarrierTest {

	private static final Logger logger = LoggerFactory.getLogger(DistributedDoubleBarrierTest.class);
	
//	private static CuratorFramework CLIENT ;
	
	public static void main(String[] args) throws Exception {
		String connectString = "localhost:2181/elim";
		//重试策略，官方提供了好几种
		RetryPolicy retryPolicy = new RetryNTimes(5, 1000);
		final CuratorFramework client = CuratorFrameworkFactory.newClient(connectString, retryPolicy);
		client.start();
		
		String barrierPath = "/double-barrier/test2";
		int memberQty = 12;//成员数
		DistributedDoubleBarrier barrier = new DistributedDoubleBarrier(client, barrierPath, memberQty);
		
		ExecutorService threadPool = Executors.newFixedThreadPool(memberQty-1);
		for (int i=0; i<5; i++) {
			threadPool.execute(new Task());
		}
//		TimeUnit.SECONDS.sleep(2);
//		CLIENT.close();//模拟客户端失去连接
//		TimeUnit.SECONDS.sleep(2);
		barrier.enter();
		barrier.leave();
		
		threadPool.shutdown();
		threadPool.awaitTermination(10, TimeUnit.SECONDS);
		
	}

	/**
	 * 要求这些线程必须属于不同的客户端，所以要分别为每一个参与者线程建立一个连接
	 * @author Elim
	 * 2017年3月14日
	 */
	public static class Task implements Runnable {

		@Override
		public void run() {
			
			String connectString = "localhost:2181/elim";
			//重试策略，官方提供了好几种
			RetryPolicy retryPolicy = new RetryNTimes(5, 1000);
			final CuratorFramework client = CuratorFrameworkFactory.newClient(connectString, retryPolicy);
			client.start();
//			CLIENT = client;
			String barrierPath = "/double-barrier/test1";
			int memberQty = 12;//成员数
			DistributedDoubleBarrier barrier = new DistributedDoubleBarrier(client, barrierPath, memberQty);
			
			
			logger.info("准备进入屏障等待");
			try {
				barrier.enter();
				logger.info("所有线程都已进入屏障等待，准备执行特定业务");
				logger.info("执行特定业务");
				
				logger.info("特定业务执行完毕，准备离开屏障");
				barrier.leave();
				logger.info("所有线程都已离开屏障");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
}
