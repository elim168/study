/**
 * 
 */
package com.elim.learn.elastic.job;

import org.apache.log4j.Logger;

import com.dangdang.ddframe.job.executor.handler.JobExceptionHandler;

/**
 * 异常处理类，用于在作业异常时做自定义的异常处理，默认的异常处理类（DefaultJobExceptionHandler）只记录日志。
 * @author Elim
 * 2016年11月15日
 */
public class MyJobExceptionHandler implements JobExceptionHandler {

	private static final Logger logger = Logger.getLogger(MyJobExceptionHandler.class);
	
	/* (non-Javadoc)
	 * @see com.dangdang.ddframe.job.executor.handler.JobExceptionHandler#handleException(java.lang.String, java.lang.Throwable)
	 */
	@Override
	public void handleException(String jobName, Throwable cause) {
		logger.error(String.format("任务[%s]调度异常", jobName), cause);
	}

}
