/**
 * 
 */
package com.elim.learn.redis.basic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.RedisConnection;
import com.lambdaworks.redis.ScriptOutputType;

/**
 * 
 * Redis也支持脚本，是基于LUA的脚本，其基本语法是：<br/>
 * eval lua_script keysNum [key1 ... keyN [arg1 ... argN]]    <br/>
 * 语法解释如下：
 * <ul>
 * 	<li>
 * 		lua_script是一段lua表达式，即eval需要解析的脚本，需要调用redis指令时我们需要使用redis.call或者redis.pcall。
 * 		如果需要获取其返回值，我们还需要在表达式的前面加上return。
 * 	</li>
 * 	<li>keysNum表示其后紧接着的Key的数量</li>
 * 	<li>key1...keyN表示可能在表达式中会用到的Key定义，其在表达式中可以通过KEYS[n]来使用，如需要用到第一个key，则使用KEYS[1]</li>
 *  <li>arg1...argN表示可能在表达式中会用到的参数ARG定义，其在表达式中可以通过ARGV[n]来使用，如需要用到第一个ARG，则使用ARGV[1]</li>
 * </ul>
 * 示例如下：
 * <ul>
 * 	<li>eval "return redis.call('set', KEYS[1], ARGV[1])" 1 abc DEF</li>
 * </ul>
 * 上述第一个示例执行的脚本就是调用redis的set指令设置名为abc的Key的值为DEF
 * 
 * <p>
 * 	redis.call()和redis.pcall()的用法是一样的，唯一的不同（在笔者看来）就是当调用的指令出现错误时，redis.call会响应的是LUA脚本错误信息，
 * 而redis.pcall()响应的信息只包含redis响应的错误信息。
 * </p>
 * <p>
 * 	<B>LUA和redis之间的数据类型转换</B>，在使用redis执行LUA脚本时，它们之间总是存在着数据类型的相互转换，具体可参考Redis的表达式章节的文档。
 * 需要注意的一点是，LUA里面只有一种数字类型，不区分整型和浮点型，在转换为redis的数据类型时，数字类型总是会被转换为整型，如果LUA对应的数字是一个
 * 浮点型数字，则转换为redis的整型时将把小数部分去除。所以如果我们希望LUA的浮点型数字能转换为redis的浮点型，我们应该把它作为一个字符串进行返回。
 * </p>
 * <p>
 * 	<b>脚本的执行跟事务一样，都是原子性的，redis会确保它们都被执行，且在执行的过程中不会执行其它的指令。</b>所以如果对应的脚本是需要花费很长时间
 * 来执行的则使用redis脚本来执行不是很合适。
 * </p>
 * <p>
 * 	用来执行脚本的除了eval指令外，还有一个指令是evalsha指令。默认情况下我们执行的脚本会被redis缓存起来，因为正常情况下，我们的脚本内容是不会变的，
 * 把脚本内容缓存起来可以减少脚本从客户端传递到服务端的带宽占用。redis会为每一个脚本利用SHA1算法生成一个摘要信息缓存起来，evalsha就是通过指定脚本
 * 对应的SHA摘要信息来执行对应的脚本的一个指令。evalsha指令的用法和eval的用法是一致的，唯一的区别在于利用evalsha执行脚本时我们需要把对应的脚本
 * 内容替换为redis缓存的脚本对应SHA摘要信息。当我们指定的一个SHA摘要信息是不存在的信息时将会报错。evalsha指令通常会和script load指令一起使用，
 * script load指令用于把加载对应的脚本内容到内存中，然后返回一个redis缓存后的SHA摘要信息，之后我们就可以利用这个摘要信息通过evalsha来执行对应
 * 的脚本。
 * </p>
 * <p>script load的语法是“script load lua_script”，其用于加载脚本内容，被由redis计算对应的SHA摘要信息，并进行缓存。
 * 如“script load "return redis.call('set',KEYS[1],ARGV[1])"”</p>
 * <p>
 * script exists指令用于判断指定的SHA摘要信息是否存在，语法是“script exists sha1”，如：
 * script exists 9c245a1596cf0dca1c4646b3b71e521c45f6bd94
 * </p>
 * <p>当我们希望清除redis缓存的脚本时，我们可以使用指令“script flush”。</p>
 * <p>如果对应的脚本执行的时间比较长，其在执行的时候我们希望停止它的执行，则可以通过指令“script kill”来杀掉当前正在执行的指令。</p>
 * 参考文档：http://redis.io/commands/eval
 * @author elim
 *
 */
public class ScriptTest {

	private static final RedisClient CLIENT = RedisClient.create("redis://localhost:6379");
	private static final Logger LOGGER = Logger.getLogger(ScriptTest.class);
	private RedisConnection<String, String> connect = null;
	
	@Before
	public void before() {
		connect = CLIENT.connect();
	}
	
	@After
	public void after() {
		connect.close();
	}
	
	/**
	 * 简单的测试脚本的执行
	 */
	@Test
	public void test1() {
		String script = "return redis.call('set', KEYS[1], ARGV[1])";
		//简单的执行一个指令
		String status = connect.eval(script, ScriptOutputType.STATUS, new String[] {"abc"}, "DEF");
		System.out.println(status);
	}
	
	/**
	 * 测试通过脚本获取类型为集合的Key的所有成员的返回类型
	 */
	@Test
	public void test2() {
		String script = "return redis.pcall('smembers', KEYS[1])";
		//这个指令的执行结果是指定set里面的所有元素，其返回的元素包含多个，我们用MULTI
		List<String> obj = connect.eval(script, ScriptOutputType.MULTI, "set");
		System.out.println(obj);
	}
	
	/**
	 * 测试通过脚本获取一个哈希类型的Key的所有field和value的返回类型
	 */
	@Test
	public void test3() {
		String key = "Map1";
		Map<String, String> map = new HashMap<>();
		map.put("A", "a");
		map.put("B", "b");
		map.put("C", "c");
		String mapResult = connect.hmset(key, map);
		LOGGER.info("设置一个Map1的结果是：" + mapResult);
		
		//获取一个类型为哈希的Key
		String script = "return redis.pcall('hgetall', KEYS[1])";
		Object eval = connect.eval(script, ScriptOutputType.MULTI, key);
		System.out.println(eval.getClass());//获取一个哈希类型的Key的所有field和value时返回的也是一个List
	}
	
	/**
	 * 测试evalsha指令的使用
	 */
	@Test
	public void test4() {
		String script = "return redis.pcall('get', KEYS[1])";
		String shaDigest = connect.scriptLoad(script);
		LOGGER.info(String.format("脚本[%s]加载后的SHA是[%s]", script, shaDigest));
		String key = "abc";
		String result = connect.evalsha(shaDigest, ScriptOutputType.VALUE, key);
		LOGGER.info(String.format("脚本[%s]的执行结果是：%s", script, result));
	}
	
}
