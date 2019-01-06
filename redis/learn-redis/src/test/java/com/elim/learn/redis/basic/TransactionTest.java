/**
 * 
 */
package com.elim.learn.redis.basic;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.RedisConnection;

/**
 * 
 * 事务测试<br/>
 * 参考官方文档http://redis.io/topics/transactions<br/>
 * <p>Redis的事务是为了保证多个指令的执行在一个事务里面，要么都执行，要么都不执行，而且在一个事务里面的指令执行期间不会执行其它的指令，
 * 也就是说一个事务里面的指令是一个接着一个执行的，执行期间不会有事务以外的其它指令被执行。而不会有类似于关系数据库的回滚机制，
 * 根据Redis官方文档的描述，之所以不会有回滚机制，是因为除了执行的指令存在语法错误外，Redis指令都能执行成功；另外
 * 一个原因就是Redis之所以快，是因为它在执行的过程中不需要考虑回滚这样的机制。</p>
 * 与Redis事务相关的有几个很重要的指令。
 * <ul>
 * 	<li>multi：用于表示一个事务的开启</li>
 * 	<li>exec：在调用了multi开启一个事务后，所有的指令都不会马上执行，而是会放在一个队列里面，只有当执行exec指令的时候，这些指令才会执行</li>
 * 	<li>discard：用于取消一个事务，取消对当前事务队列中的指令的执行</li>
 * 	<li>watch：用于监测一个或多个Key的变化，如果在一个事务开启后，执行exec之前，监测的Key被修改了，则在执行exec的时候返回结果会是空。
 * 		<b>千万要注意的是watch指令需要放到multi指令之前。</b>
 * 	</li>
 * 	<li>unwatch指令用于取消对指定Key的监测</li>
 * </ul>
 * @author elim
 *
 */
public class TransactionTest {

	private static final RedisClient CLIENT = RedisClient.create("redis://localhost:6379");
	private static final Logger LOGGER = Logger.getLogger(PubSubTest.class);
	private static final String STRKEY1 = "strKey1";
	private static final String SETKEY1 = "setKey1";
	
	private RedisConnection<String, String> connect = null;
	
	@Before
	public void before() {
		connect = CLIENT.connect();
	}
	
	@After
	public void after() {
		connect.close();
	}
	
	@Test
	public void test() {
		try {
			connect.multi();//开始一个事务
			connect.append(STRKEY1, "A");
			connect.append(STRKEY1, "B");
			connect.append(STRKEY1, "C");
			connect.sadd(SETKEY1, "DEF");//声明了一个Set
			connect.append(SETKEY1, "D");//会抛出异常，这个方法属于String类型的Key的，Set不能执行这个方法
			connect.sadd(SETKEY1, "ABC");
			//开启了事务机制后，对应的指令不会马上执行，而是会加入一个队列，在调用exec时才会真正执行，所以这里暂时还拿不到结果
			LOGGER.info(connect.get(STRKEY1));
			//返回结果是每一行指令的执行结果
			List<Object> list = connect.exec();
			System.out.println(list);
			for (Object result : list) {
				if (result instanceof Exception) {
					LOGGER.error("指令执行异常", (Throwable)result);
				}
			}
		} catch (Exception e) {
			LOGGER.error("异常了", e);
			connect.discard();//调用discard用于取消事务，取消对应指令的执行
		}
	}
	
	@Test
	public void test2() throws InterruptedException {
		connect.watch(STRKEY1);//监测指定的Key
		connect.multi();
		TimeUnit.SECONDS.sleep(10);//休眠10s，等待其它客户端来改变STRKEY1
		connect.append(STRKEY1, "ABC");
		try {
			List<Object> results = connect.exec();
			LOGGER.info("执行结果是：" + results);
		} catch (Exception e) {//在执行exec指令时如果当前客户端监测了的Key的内容发生了变化，将抛出异常
			LOGGER.error("执行exec指令异常", e);
		}
	}
	
	@Test
	public void test3() {
		connect.append(STRKEY1, "DBA");//对transactionTest1的内容进行修改
	}
	
}
