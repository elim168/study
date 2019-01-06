package com.elim.learn.elastic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;

/**
 * 验证动态的注册作业的情形
 * @author Elim
 * 2017年11月8日
 */
public class RegistSimpleJob implements SimpleJob {

    private static final Logger logger = LoggerFactory.getLogger(RegistSimpleJob.class);
    
    @Override
    public void execute(ShardingContext shardingContext) {
        logger.info("作业调度，shardingContext是： " + shardingContext);
    }

}
