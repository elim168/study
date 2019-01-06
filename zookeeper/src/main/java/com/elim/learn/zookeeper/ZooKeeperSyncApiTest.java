/**
 * 
 */
package com.elim.learn.zookeeper;

import java.io.IOException;
import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 测试原生的ZooKeeper API的同步调用方式
 * @author Elim
 * 2017年3月6日
 */
public class ZooKeeperSyncApiTest {

	private ZooKeeper zookeeper;
	private static final String PATH = "/elim_test";
	private static final Logger logger = LoggerFactory.getLogger(ZooKeeperSyncApiTest.class);
	
	public static void main(String args[]) throws KeeperException, InterruptedException, IOException {
		ZooKeeperSyncApiTest test = new ZooKeeperSyncApiTest();
		test.init();
		test.create();
		test.delete();
		test.update();
		test.findData();
		test.findChildren();
		test.exist();
	}
	
	public void init() throws IOException {
		int sessionTimeout = 5000;
		Watcher watcher = null;//指定默认的Watcher
		zookeeper = new ZooKeeper("localhost:2181", sessionTimeout, watcher);
	}
	
	/**
	 * 创建节点，CreateMode有四种类型，分别是持久节点、临时节点，及各自的顺序节点。临时节点在当前连接断开后就自动清除了
	 * @throws KeeperException
	 * @throws InterruptedException
	 */
	public void create() throws KeeperException, InterruptedException {
		List<ACL> acl = Ids.OPEN_ACL_UNSAFE;
		String realPath = zookeeper.create(PATH, "data".getBytes(), acl, CreateMode.EPHEMERAL_SEQUENTIAL);
		logger.info("创建一个临时顺序节点，path=" + PATH + "，真实的节点路径是：" + realPath);
		//创建一个临时节点
		zookeeper.create(PATH, "data".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
	}
	
	/**
	 * 删除节点
	 * @throws KeeperException
	 * @throws InterruptedException
	 */
	public void delete() throws KeeperException, InterruptedException {
		//创建一个持久化的顺序节点
		String realPath = zookeeper.create(PATH, "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL);
		int version = -1;
		//删除一个节点，对应的版本号必须要匹配才能删除，版本号传递-1可匹配任意版本号
		zookeeper.delete(realPath, version);
	}
	
	/**
	 * 更新节点，更新的是节点的数据内容
	 * @throws KeeperException
	 * @throws InterruptedException
	 */
	public void update() throws KeeperException, InterruptedException {
		int version = -1;
		//修改节点，能修改的就只有节点的内容，修改时必须指定版本号，只有版本号匹配上了才会进行修改操作，传递-1可匹配所有的版本号
		Stat stat = zookeeper.setData(PATH, "data2".getBytes(), version);
		logger.info("更新后的状态信息是：" + stat);
	}
	
	/**
	 * 获取节点的数据内容
	 * @throws KeeperException
	 * @throws InterruptedException
	 */
	public void findData() throws KeeperException, InterruptedException {
		Stat stat = new Stat();
		boolean watch = false;
		//返回的是对应节点的数据内容，传递watch为true时表示需要使用默认的Watcher，其会在对应的节点的数据更新或节点删除时被回调；
		//stat是用于接收节点的状态信息的，包括事务ID、创建时间等，如果传递的stat对象与真实的Stat内容不同，则会把真实的内容赋值给传递的Stat
		byte[] data = zookeeper.getData(PATH, watch, stat);
		logger.info("获取到的节点的内容是：" + new String(data));
	}
	
	/**
	 * 取子节点内容
	 * @throws KeeperException
	 * @throws InterruptedException
	 */
	public void findChildren() throws KeeperException, InterruptedException {
		boolean watch = false;
		//获取指定路径的节点的子节点内容，只取下一级的
		List<String> children = zookeeper.getChildren("/", watch);
		logger.info("获取到的子节点列表是：" + children);
	}
	
	/**
	 * 判断指定路径的节点是否存在
	 * @throws KeeperException
	 * @throws InterruptedException
	 */
	public void exist() throws KeeperException, InterruptedException {
		boolean watch = false;
		//判断对应路径的节点是否存在，返回的结果不为null则表示存在，否则表示不存在
		Stat exists = zookeeper.exists(PATH, watch);
		logger.info("判断节点是否存在的结果是：" + (exists != null ? true : false));
	}
	
}
