/**
 * 
 */
package com.elim.learn.elastic.job.listener;

import org.apache.log4j.Logger;

import com.dangdang.ddframe.job.executor.ShardingContexts;
import com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener;

/**
 * 任务调度时候的监听器，每个任务只能配置一个监听器。可参考当当源码com.dangdang.ddframe.job.lite.spring.namespace.JobNamespaceHandler对这块的解析
 * ElasticJobListener的两个方法的触发时间，一个是在任务调度前，一个是在之后。具体可参考com.dangdang.ddframe.job.executor.AbstractElasticJobExecutor.execute()方法。
 * @author Elim
 * 2016年11月3日
 */
public class MyElasticJobListener implements ElasticJobListener {

	private static final Logger LOGGER = Logger.getLogger(MyElasticJobListener.class);
	
	@Override
	public void beforeJobExecuted(ShardingContexts shardingContexts) {
		LOGGER.info(String.format("开始调度任务[%s]", shardingContexts.getJobName()));
	}

	@Override
	public void afterJobExecuted(ShardingContexts shardingContexts) {
		LOGGER.info(String.format("任务[%s]调度完成", shardingContexts.getJobName()));
	}

}
