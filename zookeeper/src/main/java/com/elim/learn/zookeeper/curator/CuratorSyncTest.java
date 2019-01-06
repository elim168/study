/**
 * 
 */
package com.elim.learn.zookeeper.curator;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.framework.recipes.leader.CancelLeadershipException;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListener;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 使用Curator对ZooKeeper进行操作的同步API测试
 * @author Elim
 * 2017年3月11日
 */
public class CuratorSyncTest {
	
	private CuratorFramework client;
	
	private static final String DEFAULT_PATH = "/first/second/third/leaf";
	private static final Logger logger = LoggerFactory.getLogger(CuratorSyncTest.class);
	
	public CuratorSyncTest() throws InterruptedException {
		RetryPolicy retryPolicy = new RetryNTimes(3, 1000);
		//指定默认容器路径是/elim2，即namespace是elim2
//		client = CuratorFrameworkFactory.newClient("localhost:2181/elim2", retryPolicy);
		client = CuratorFrameworkFactory.builder()
					.connectString("localhost:2181")
					.namespace("elim2")
					.retryPolicy(retryPolicy)
					.defaultData("defaultData".getBytes())//指定创建节点时没有指定数据时使用的默认数据
					.sessionTimeoutMs(5000)
					.build();
		client.start();
		client.blockUntilConnected();
//		client.blockUntilConnected(10, TimeUnit.SECONDS);
		try {
			this.initPathChildrenCache();
			this.initNodeCache();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 监听节点的子节点内容的变化
	 * @throws Exception
	 */
	@SuppressWarnings("resource")
	private void initPathChildrenCache() throws Exception {
		boolean cacheData = true;
		PathChildrenCache pathChildrenCache = new PathChildrenCache(client, "/elim2", cacheData);
		pathChildrenCache.getListenable().addListener(new PathChildrenCacheListener() {

			@Override
			public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
				logger.info("监听到节点/elim2的子节点发生了[{}]事件：{}", event.getType(), event);
			}
			
		});
		pathChildrenCache.start();
		
	}
	
	/**
	 * 监听节点数据的变化
	 * @throws Exception
	 */
	@SuppressWarnings("resource")
	private void initNodeCache() throws Exception {
		boolean dataIsCompressed = true;
		final NodeCache cache = new NodeCache(client, DEFAULT_PATH, dataIsCompressed);
		cache.getListenable().addListener(new NodeCacheListener() {

			@Override
			public void nodeChanged() throws Exception {
				logger.info("节点[{}]的数据发生了变化，当前数据是：{}", DEFAULT_PATH, new String(cache.getCurrentData().getData()));
			}
			
		});
		cache.start();
	}
	
	public static void main(String args[]) throws Exception {
		CuratorSyncTest test = new CuratorSyncTest();
		test.checkExists();
		test.create();
		test.delete();
		test.getData();
		test.setData();
		test.getChildren();
		test.leaderSelector();
		TimeUnit.SECONDS.sleep(10);
	}
	
	/**
	 * 创建节点
	 * @throws Exception
	 */
	private void create() throws Exception {
		String forPathResult = client.create()//对应的CreateBuilder
			.compressed()//对节点数据进行压缩
			.creatingParentContainersIfNeeded()//指定的容器路径不存在时自动创建，创建节点时父节点不存在时自动创建
//			.creatingParentsIfNeeded()//父节点不存在时会自动创建
			.withMode(CreateMode.EPHEMERAL)//模式是临时节点，但是ZooKeeper只有叶子节点才能是临时节点,当客户端关闭时，只有最后的叶子节点leaf会自动删除，其上层目录都将保留
			.forPath(DEFAULT_PATH, "data".getBytes());//创建节点，指定对应的数据，也可以不指定data，这个时候data就取默认数据
		logger.info("forPath方法的返回结果是创建的节点的真实路径：" + forPathResult);
	}

	/**
	 * 删除节点
	 * @throws Exception
	 */
	private void delete() throws Exception {
		//默认不指定CreateMode时，forPath创建的节点是永久节点
		String realPath = client.create().forPath("/node_for_delete");
		client.delete()//对应的是DeleteBuilder
			.guaranteed()//确保一定会执行成功，在删除失败时，Curator后台会一直尝试删除，直到成功为止。当然删除失败时它会抛出异常，但是它还是会不断尝试。
			.deletingChildrenIfNeeded()//默认情况下节点下面有子节点时是不能删除的，加上这个选项会连子节点一起删除，然后再删除指定的节点
			.withVersion(-1)//删除指定版本号的节点，避免删错。传递-1表示忽略版本号，默认就是忽略版本号的
			.forPath(realPath);
		logger.info("节点[{}]删除成功", realPath);
	}
	
	/**
	 * 获取数据
	 * @throws Exception
	 */
	private void getData() throws Exception {
		Stat stat = new Stat();
		byte[] data = client.getData()//对应GetDataBuilder
			.decompressed()//存入的数据是压缩过的，取数据时就进行解压缩
			.storingStatIn(stat)//把状态信息存入指定的Stat对象
			.forPath(DEFAULT_PATH);
		logger.info("获取路径{}的节点数据[{}]，stat信息是{}", new Object[] {DEFAULT_PATH, new String(data), stat});
	}
	
	/**
	 * 更新数据
	 * @throws Exception
	 */
	private void setData() throws Exception {
		Stat stat = client.setData()//对应SetDataBuilder
			.compressed()
			.withVersion(-1)//指定版本号，方便进行CAS操作
			.forPath(DEFAULT_PATH, "new data".getBytes());
		logger.info("对路径[{}]设置了新数据后的Stat信息是：{}", DEFAULT_PATH, stat);
		
	}
	
	/**
	 * 获取子节点数据
	 * @throws Exception
	 */
	private void getChildren() throws Exception {
		List<String> children = client.getChildren()//对应GetChildrenBuilder
			.forPath("/");
		logger.info("子节点路径有：{}", children);
	}

	/**
	 * 判断节点是否存在
	 * @throws Exception
	 */
	private void checkExists() throws Exception {
		Stat stat = client.checkExists()//对应ExistsBuilder
			.forPath(DEFAULT_PATH);
		logger.info("校验节点是否存在的结果是：{}", stat != null);
	}
	
	@SuppressWarnings("resource")
	private void leaderSelector() {
		LeaderSelector leaderSelector = new LeaderSelector(client, "/elim1/leader_selector", new LeaderSelectorListener() {
			
			@Override
			public void stateChanged(CuratorFramework client, ConnectionState newState) {
				if (newState == ConnectionState.SUSPENDED || newState == ConnectionState.LOST) {
					throw new CancelLeadershipException();
				}
			}
			
			@Override
			public void takeLeadership(CuratorFramework client) throws Exception {
				logger.info("当前节点成为了领导者了");
			}
		});
		leaderSelector.autoRequeue();
		leaderSelector.start();
	}
	
}
