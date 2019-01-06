/**
 * 
 */
package com.elim.learn.elastic.job.listener;

import org.apache.log4j.Logger;

import com.dangdang.ddframe.job.executor.ShardingContexts;
import com.dangdang.ddframe.job.lite.api.listener.AbstractDistributeOnceElasticJobListener;

/**
 * 普通的ElasticJobListener监听器在分布式任务中每个节点在执行自己的分片的任务时都将执行对应的监听器的方法，这有的时候可能并不满足我们的需要，因为有的时候，
 * 我们可能只希望我们的监听程序只执行一次。AbstractDistributeOnceElasticJobListener监听器就可以实现这个功能，其对应的监听器方法将只会执行一遍，而且是
 * 在所有的作业都启动或都完成后才进行调度。具体这种效果是怎么实现的请参考AbstractDistributeOnceElasticJobListener的源码，其是在ElasticJobListener
 * 的基础上进行了扩展。
 * @author Elim
 * 2016年11月14日
 */
public class MyDistributeOnceElasticJobListener extends AbstractDistributeOnceElasticJobListener {

	private static final Logger logger = Logger.getLogger(MyDistributeOnceElasticJobListener.class);
	
	/**
	 * @param startedTimeoutMilliseconds
	 * @param completedTimeoutMilliseconds
	 */
	public MyDistributeOnceElasticJobListener(long startedTimeoutMilliseconds, long completedTimeoutMilliseconds) {
		super(startedTimeoutMilliseconds, completedTimeoutMilliseconds);
	}

	@Override
	public void doBeforeJobExecutedAtLastStarted(ShardingContexts shardingContexts) {
		logger.info("分布式监听器开始……");
	}

	@Override
	public void doAfterJobExecutedAtLastCompleted(ShardingContexts shardingContexts) {
		logger.info("分布式监听器结束……");
	}

}
