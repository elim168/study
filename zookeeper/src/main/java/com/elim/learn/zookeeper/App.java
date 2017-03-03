package com.elim.learn.zookeeper;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooKeeper.States;
import org.apache.zookeeper.data.Stat;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
		String connectString = "10.192.48.170:2181,10.192.48.170:2182/elim";
		Watcher watcher = null;
		ZooKeeper zookeeper = new ZooKeeper(connectString, 3000, watcher);
		States state = zookeeper.getState();
		while (state != States.CONNECTED) {
			TimeUnit.MILLISECONDS.sleep(100);
			state = zookeeper.getState();
			System.out.println(state);
		}
		create(zookeeper, "/dir-persist", CreateMode.PERSISTENT);
		create(zookeeper, "/dir-persist-seq", CreateMode.PERSISTENT_SEQUENTIAL);
		create(zookeeper, "/dir-ephemeral", CreateMode.EPHEMERAL);
		create(zookeeper, "/dir-ephemeral-seq", CreateMode.EPHEMERAL_SEQUENTIAL);
		
		System.out.println(zookeeper.getChildren("/", watcher));
		
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
