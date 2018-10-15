package com.atguigu.zk;

import java.io.IOException;
import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.junit.Before;
import org.junit.Test;

public class TestZookeeper {

	// 连接zkServer
	private String connectString = "192.168.2.130:2181,192.168.2.131:2181,192.168.2.132:2181";
	// 连接超时时间设置
	private int sessionTimeout = 2000;

	ZooKeeper zkClient = null;

	// 初始化zk客户端
	@Before
	public void initClient() throws IOException {
		zkClient = new ZooKeeper(connectString, sessionTimeout, new Watcher() {

			@Override
			public void process(WatchedEvent event) {
				// 收到事件通知后的回调函数（用户的业务逻辑）
				System.out.println(event.getType() + "--" + event.getPath());
				// 再次启动监听
				try {
					zkClient.getChildren("/", true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	//创建子节点
	@Test
	public void createNode() throws KeeperException, InterruptedException {
		// 数据的增删改查
		// 参数 1：要创建的节点的路径； 参数 2：节点数据 ； 参数 3：节点权限 ；参数 4：节点的类型
		String nodeCreated = zkClient.create("/atguigu", "hello  zk".getBytes(), Ids.OPEN_ACL_UNSAFE,
				CreateMode.PERSISTENT);
		System.out.println(nodeCreated);
	}

	//获取子节点
	@Test
	public void getChildren() throws KeeperException, InterruptedException {
		List<String> children = zkClient.getChildren("/", true);
		for (String child : children) {
			System.out.println(child);
		}
		// 延时阻塞
		Thread.sleep(Long.MAX_VALUE);
	}
}
