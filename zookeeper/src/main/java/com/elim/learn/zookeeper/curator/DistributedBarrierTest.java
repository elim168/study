/**
 * 
 */
package com.elim.learn.zookeeper.curator;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.barriers.DistributedBarrier;
import org.apache.curator.retry.RetryNTimes;

/**
 * 本示例的用法是用Curator提供的DistributedBarrier实现类似JDK的CyclicBarrier的功能。
 * 其实Curator已经为我们封装了一个类似于JDK的CyclicBarrier的分布式屏障DistributedDoubleBarrier，我们可以直接用它来实现
 * 和CyclicBarrier一样的功能
 * @author Elim
 * 2017年3月13日
 */
public class DistributedBarrierTest {
	
	public static void main(String[] args) throws Exception {
		String connectString = "localhost:2181/elim";
		//重试策略，官方提供了好几种
		RetryPolicy retryPolicy = new RetryNTimes(5, 1000);
		final CuratorFramework client = CuratorFrameworkFactory.newClient(connectString, retryPolicy);
		client.start();
		String barrierPath = "/barrier/test1";
		DistributedBarrier barrier = new DistributedBarrier(client, barrierPath);
		barrier.setBarrier();//设置屏障
		int nThreads = 5;
		ExecutorService pool = Executors.newFixedThreadPool(nThreads);
		AtomicInteger counter = new AtomicInteger(nThreads);
		for (int i=0; i<nThreads; i++) {
			pool.execute(new Task(counter));
		}
		while (counter.get() > 0) {
			TimeUnit.MILLISECONDS.sleep(10);
		}
		barrier.removeBarrier();//清除屏障
		pool.shutdown();
		pool.awaitTermination(10, TimeUnit.SECONDS);
	}

	public static class Task implements Runnable {
		
		private AtomicInteger counter;
		
		public Task(AtomicInteger counter) {
			this.counter = counter;
		}

		@Override
		public void run() {
			String connectString = "localhost:2181/elim";
			//重试策略，官方提供了好几种
			RetryPolicy retryPolicy = new RetryNTimes(5, 1000);
			final CuratorFramework client = CuratorFrameworkFactory.newClient(connectString, retryPolicy);
			client.start();
			String barrierPath = "/barrier/test1";
			DistributedBarrier barrier = new DistributedBarrier(client, barrierPath);
			try {
				System.out.println(Thread.currentThread().getName() + "进入准备状态");
				this.counter.decrementAndGet();
				barrier.waitOnBarrier();//等待屏障清除，阻塞式的
				System.out.println(Thread.currentThread().getName() + "准备执行");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
}
