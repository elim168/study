/**
 * 
 */
package com.elim.learn.elastic.job;

import org.apache.log4j.Logger;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;

/**
 * 执行时将会抛出异常的一个Job，用于测试JobExceptionHandler
 * @author Elim
 * 2016年11月15日
 */
public class MyExceptionJob implements SimpleJob {

	private static final Logger logger = Logger.getLogger(MyExceptionJob.class);
	
	/* (non-Javadoc)
	 * @see com.dangdang.ddframe.job.api.simple.SimpleJob#execute(com.dangdang.ddframe.job.api.ShardingContext)
	 */
	@Override
	public void execute(ShardingContext shardingContext) {
		if (shardingContext.getShardingItem() == 1) {
			throw new RuntimeException("只在分片为1的时候才抛出异常，用于测试异常处理");
		}
		logger.info(shardingContext);
	}

}
