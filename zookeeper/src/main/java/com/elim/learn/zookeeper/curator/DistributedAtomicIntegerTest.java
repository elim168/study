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
import org.apache.curator.framework.recipes.atomic.DistributedAtomicInteger;
import org.apache.curator.retry.RetryNTimes;

/**
 * @author Elim
 * 2017年3月13日
 */
public class DistributedAtomicIntegerTest {

	public static void main(String[] args) throws Exception {
		String connectString = "localhost:2181/elim";
		//重试策略，官方提供了好几种
		RetryPolicy retryPolicy = new RetryNTimes(5, 1000);
		final CuratorFramework client = CuratorFrameworkFactory.newClient(connectString, retryPolicy);
		client.start();
		String counterPath = "/counter/test2";
		DistributedAtomicInteger counter = new DistributedAtomicInteger(client, counterPath, retryPolicy);
		
		boolean initialized = counter.initialize(0);
		if (!initialized) {
			boolean initializedSuccess = counter.trySet(0).succeeded();
			System.out.println(initializedSuccess);
		}
		int nThreads = 5;
		ExecutorService pool = Executors.newFixedThreadPool(nThreads);
		for (int i=0; i<nThreads; i++) {
			pool.execute(new Task());
		}
		pool.shutdown();
		pool.awaitTermination(100, TimeUnit.SECONDS);
		System.out.println("最终结果是：" + counter.get().preValue());
	}
	
	public static class Task implements Runnable {

		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			String connectString = "localhost:2181/elim";
			//重试策略，官方提供了好几种
			RetryPolicy retryPolicy = new RetryNTimes(5, 1000);
			final CuratorFramework client = CuratorFrameworkFactory.newClient(connectString, retryPolicy);
			client.start();
			String counterPath = "/counter/test2";
			DistributedAtomicInteger counter = new DistributedAtomicInteger(client, counterPath, retryPolicy);
			long start = System.currentTimeMillis();
			for (int i=0; i<10; i++) {
				Integer value = 0;
				try {
					value = counter.increment().postValue();
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println(value);
			}
			long end = System.currentTimeMillis();
			System.out.println("耗时：" + (end - start));
		}
		
	}
	
}
