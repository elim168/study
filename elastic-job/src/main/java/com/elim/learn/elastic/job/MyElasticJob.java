/**
 * 
 */
package com.elim.learn.elastic.job;

import org.apache.log4j.Logger;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;

/**
 * 普通作业，与Quartz的定时作业类似，只是会多了分片等功能
 * @author Elim
 * 2016年10月29日
 */
public class MyElasticJob implements SimpleJob {
	
	private static final Logger LOGGER = Logger.getLogger(MyElasticJob.class);

	@Override
	public void execute(ShardingContext context) {
		//当你的作业是分片的时候，你需要在你的Job的execute方法中根据当前的分片shardingItem的不同取值实现不同的逻辑，
		//要把所有的shardingItem都覆盖到，因为在分布式环境，每台机器都不能确保它当前的分片是哪一个，并且我们需要保持程序
		//的一致性，程序编写好了对部署是不会有影响的。
		int shardingItem = context.getShardingItem();
		switch (shardingItem) {
			case 0:
				LOGGER.info("处理第一个分片");
				break;
			case 1: 
				LOGGER.info("处理第二个分片");
				break;
			case 2:
				LOGGER.info("处理第三个分片");
				break;
			case 3:
				LOGGER.info("处理第四个分片");
				break;
			case 4:
				LOGGER.info("处理第五个分片");
				break;
			case 5:
				LOGGER.info("处理第六个分片");
				break;
		}
		LOGGER.info(context);
	}

}
