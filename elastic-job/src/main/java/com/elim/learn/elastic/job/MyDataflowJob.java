/**
 * 
 */
package com.elim.learn.elastic.job;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;

/**
 * 流式作业，每次调度触发的时候都会先调fetchData获取数据，如果获取到了数据再调度processData方法处理数据。
 * DataflowJob在运行时有两种方式，流式的和非流式的，通过属性streamingProcess控制，如果是基于Spring XML的配置
 * 方式则是streaming-process属性，boolean类型。当作业配置为流式的时候，每次触发作业后会调度一次fetchData获取数据，
 * 如果获取到了数据会调度processData方法处理数据，处理完后又继续调fetchData获取数据，再调processData处理，如此循环，
 * 就像流水一样。直到fetchData没有获取到数据或者发生了重新分片才会停止。
 * 代码实现部分可参考数据流执行器{@link com.dangdang.ddframe.job.executor.type.DataflowJobExecutor}
 * @author Elim
 * 2016年11月2日
 * @see com.dangdang.ddframe.job.executor.type.DataflowJobExecutor
 */
public class MyDataflowJob implements DataflowJob<String> {
	
	private static final ThreadLocal<Integer> LOOP_COUNTER = new ThreadLocal<>();
	private static final int LOOP_TIMES = 10;//每次获取流处理循环次数
	private static final AtomicInteger COUNTER = new AtomicInteger(1);//计数器

	@Override
	public List<String> fetchData(ShardingContext shardingContext) {
		Integer current = LOOP_COUNTER.get();
		if (current == null) {
			current = 1;
		} else {
			current += 1;
		}
		LOOP_COUNTER.set(current);
		System.out.println(Thread.currentThread() + "------------current--------" + current);
		if (current > LOOP_TIMES) {
			System.out.println("\n\n\n\n");
			return null;
		} else {
			int shardingItem = shardingContext.getShardingItem();
			List<String> datas = Arrays.asList(getData(shardingItem), getData(shardingItem), getData(shardingItem));
			return datas;
		}
	}
	
	private String getData(int shardingItem) {
		return shardingItem + "-" + COUNTER.getAndIncrement();
	}

	@Override
	public void processData(ShardingContext shardingContext, List<String> data) {
		System.out.println(Thread.currentThread() + "--------" +data);
	}

}
