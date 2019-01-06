/**
 * 
 */
package com.elim.learn.zookeeper;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.zookeeper.AsyncCallback.ChildrenCallback;
import org.apache.zookeeper.AsyncCallback.DataCallback;
import org.apache.zookeeper.AsyncCallback.StatCallback;
import org.apache.zookeeper.AsyncCallback.StringCallback;
import org.apache.zookeeper.AsyncCallback.VoidCallback;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.KeeperException.Code;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 测试原生的ZooKeeper API的异步调用方式
 * @author Elim
 * 2017年3月6日
 */
public class ZooKeeperAsyncApiTest {

	private ZooKeeper zookeeper;
	private static final String PATH = "/elim_test";
	private static final Logger logger = LoggerFactory.getLogger(ZooKeeperAsyncApiTest.class);
	
	public static void main(String args[]) throws KeeperException, InterruptedException, IOException {
		ZooKeeperAsyncApiTest test = new ZooKeeperAsyncApiTest();
		test.init();
		test.create();
		test.delete();
		test.update();
		test.findData();
		test.findChildren();
		test.exist();
		TimeUnit.SECONDS.sleep(10);
	}
	
	public void init() throws IOException, KeeperException, InterruptedException {
		int sessionTimeout = 5000;
		Watcher watcher = new DefaultWatcher();//指定默认的Watcher
		zookeeper = new ZooKeeper("localhost:2181", sessionTimeout, watcher);
		//创建一个临时节点供后续使用
		zookeeper.create(PATH, "data".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
	}
	
	/**
	 * 创建节点，CreateMode有四种类型，分别是持久节点、临时节点，及各自的顺序节点。临时节点在当前连接断开后就自动清除了
	 * @throws KeeperException
	 * @throws InterruptedException
	 */
	public void create() throws KeeperException, InterruptedException {
		List<ACL> acl = Ids.OPEN_ACL_UNSAFE;//权限信息
		Object ctx = 1;//携带的上下文对象
		zookeeper.create(PATH, "data".getBytes(), acl, CreateMode.EPHEMERAL_SEQUENTIAL, new StringCallback() {
			
			@Override
			public void processResult(int rc, String path, Object ctx, String name) {
				logger.info("异步创建节点的处理结果是：");
				logger.info("返回码是：" + rc);
				logger.info("携带的上下文对象是：" + ctx);
				if (rc == Code.OK.intValue()) {
					logger.info("创建一个临时顺序节点，path=" + path + "，真实的节点路径是：" + name);
				}
			}
			
		}, ctx);
		
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
		Object ctx = null;
		//删除一个节点，对应的版本号必须要匹配才能删除，版本号传递-1可匹配任意版本号
		zookeeper.delete(realPath, version, new VoidCallback() {
			
			@Override
			public void processResult(int rc, String path, Object ctx) {
				logger.info("异步删除节点的处理结果是：");
				logger.info("返回码是：" + rc);
				logger.info("携带的上下文对象是：" + ctx);
				logger.info("删除的节点是：" + path);
			}
			
		}, ctx);
	}
	
	/**
	 * 更新节点，更新的是节点的数据内容
	 * @throws KeeperException
	 * @throws InterruptedException
	 */
	public void update() throws KeeperException, InterruptedException {
		int version = -1;
		Object ctx = null;
		//修改节点，能修改的就只有节点的内容，修改时必须指定版本号，只有版本号匹配上了才会进行修改操作，传递-1可匹配所有的版本号
		zookeeper.setData(PATH, "data2".getBytes(), version, new StatCallback() {
			
			@Override
			public void processResult(int rc, String path, Object ctx, Stat stat) {
				logger.info("异步更新节点的处理结果是：");
				logger.info("返回码是：" + rc);
				logger.info("携带的上下文对象是：" + ctx);
				logger.info("节点更新后的状态信息是：" + stat);
				logger.info("更新的节点是：" + path);
				
			}
		}, ctx);
	}
	
	/**
	 * 获取节点的数据内容
	 * @throws KeeperException
	 * @throws InterruptedException
	 */
	public void findData() throws KeeperException, InterruptedException {
		boolean watch = true;
		Object ctx = null;
		zookeeper.getData(PATH, watch, new DataCallback() {
			
			@Override
			public void processResult(int rc, String path, Object ctx, byte[] data, Stat stat) {
				logger.info("异步获取节点数据的处理结果是：");
				logger.info("返回码是：" + rc);
				logger.info("携带的上下文对象是：" + ctx);
				logger.info("节点的状态信息是：" + stat);
				logger.info("获取数据的节点是：" + path);
				logger.info("获取到的数据是：" + new String(data));
			}
		}, ctx);
	}
	
	/**
	 * 取子节点内容
	 * @throws KeeperException
	 * @throws InterruptedException
	 */
	public void findChildren() throws KeeperException, InterruptedException {
		boolean watch = true;
		Object ctx = null;
		//还有一个类型为Children2Callback的回调函数
		zookeeper.getChildren(PATH, watch, new ChildrenCallback() {
			
			@Override
			public void processResult(int rc, String path, Object ctx, List<String> children) {
				logger.info("异步获取子节点列表的处理结果是：");
				logger.info("返回码是：" + rc);
				logger.info("携带的上下文对象是：" + ctx);
				logger.info("获取子节点列表的路径是：" + path);
				logger.info("获取到的子节点列表是：" + children);
			}
		}, ctx);
	}
	
	/**
	 * 判断指定路径的节点是否存在
	 * @throws KeeperException
	 * @throws InterruptedException
	 */
	public void exist() throws KeeperException, InterruptedException {
		boolean watch = true;
		Object ctx = null;
		zookeeper.exists(PATH, watch, new StatCallback() {
			
			@Override
			public void processResult(int rc, String path, Object ctx, Stat stat) {
				logger.info("异步判断节点是否存在的处理结果是：");
				logger.info("返回码是：" + rc);
				logger.info("携带的上下文对象是：" + ctx);
				logger.info("异步判断节点是否存在的节点路径是：" + path);
				logger.info("异步判断节点是否存在获取到的结果是：" + stat);
			}
		}, ctx);
	}
	
	private static class DefaultWatcher implements Watcher {

		@Override
		public void process(WatchedEvent event) {
			logger.info("收到一个默认的Watcher处理的事件：" + event);
		}
		
	}
	
}
