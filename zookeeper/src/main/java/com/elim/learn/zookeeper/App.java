package com.elim.learn.zookeeper;

import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

/**
 * Hello world!
 * 使用ZooKeeper自带的客户端
 *
 */
public class App {
	public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
		String connectString = "10.192.48.170:2181,10.192.48.170:2182/elim";
		Watcher watcher = null;
		//new了一个ZooKeeper对象后，底层就启动了两个线程，一个是SendThread，一个是EventThread，SendThread就在连接ZooKeeper服务器了
		ZooKeeper zookeeper = new ZooKeeper(connectString, 3000, watcher);
		create(zookeeper, "/dir-persist", CreateMode.PERSISTENT);
		create(zookeeper, "/dir-persist-seq", CreateMode.PERSISTENT_SEQUENTIAL);
		create(zookeeper, "/dir-ephemeral", CreateMode.EPHEMERAL);
		create(zookeeper, "/dir-ephemeral-seq", CreateMode.EPHEMERAL_SEQUENTIAL);
		
		System.out.println(zookeeper.getChildren("/", watcher));
		
		int version = -1;//version为-1将匹配所有的版本号
		zookeeper.setData("/dir-persist", "ABC".getBytes(), version);
		byte[] data = zookeeper.getData("/dir-persist", false, null);
		System.out.println(new String(data));
	}

	private static void create(ZooKeeper zookeeper, String path, CreateMode mode) throws KeeperException, InterruptedException {
		Stat stat = zookeeper.exists(path, false);
		if (stat != null) {
			System.out.println(path + "已存在，先删除……");
			zookeeper.delete(path, stat.getVersion());
		}
		String result = zookeeper.create(path, path.getBytes(), Ids.OPEN_ACL_UNSAFE, mode);
		System.out.println(path + "====================" + result);
	}
}
