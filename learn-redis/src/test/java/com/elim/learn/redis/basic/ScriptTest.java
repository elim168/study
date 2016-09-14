/**
 * 
 */
package com.elim.learn.redis.basic;

import java.util.List;

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
 * 	需要注意的一点是，LUA里面只有一种数字类型，不区分整型和浮点型，在转换为redis的数据类型时，数字类型总是会被转换为整型，如果LUA对应的数字是一个
 * 	浮点型数字，则转换为redis的整型时将把小数部分去除。所以如果我们希望LUA的浮点型数字能转换为redis的浮点型，我们应该把它作为一个字符串进行返回。
 * </p>
 * 参考文档：http://redis.io/commands/eval
 * @author elim
 *
 */
public class ScriptTest {

	private static final RedisClient CLIENT = RedisClient.create("redis://localhost:6379");
	private RedisConnection<String, String> connect = null;
	
	@Before
	public void before() {
		connect = CLIENT.connect();
	}
	
	@Test
	public void test1() {
		String script = "return redis.call('set', KEYS[1], ARGV[1])";
		String status = connect.eval(script, ScriptOutputType.STATUS, new String[] {"abc"}, "DEF");
		System.out.println(status);
	}
	
	@Test
	public void test2() {
		String script = "return redis.pcall('smembers', KEYS[1])";
		List<String> obj = connect.eval(script, ScriptOutputType.MULTI, "set");
		System.out.println(obj);
	}
	
}
