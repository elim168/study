/**
 * 
 */
package com.elim.learn.elastic.job;

import org.apache.log4j.Logger;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;

/**
 * @author Elim
 * 2016年10月29日
 */
public class MyElasticJob implements SimpleJob {
	
	private static final Logger LOGGER = Logger.getLogger(MyElasticJob.class);

	/* (non-Javadoc)
	 * @see com.dangdang.ddframe.job.api.simple.SimpleJob#execute(com.dangdang.ddframe.job.api.ShardingContext)
	 */
	@Override
	public void execute(ShardingContext context) {
		LOGGER.info(context);
	}

}
