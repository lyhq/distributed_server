package com.atguigu.zk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

public class ZkClient {

	private String connectString = "192.168.2.130:2181,192.168.2.131:2181,192.168.2.132:2181";
	private int sessionTimeout = 2000;
	private ZooKeeper zkClient = null;

	public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
		ZkClient client = new ZkClient();

		// 1、获取连接
		client.getConnection();
		// 2、监听服务器节点路径
		client.getServerList();
		// 3、业务处理
		client.business();
	}

	/**
	 * 业务处理
	 * 
	 * @throws InterruptedException
	 */
	private void business() throws InterruptedException {
		Thread.sleep(Long.MAX_VALUE);
	}

	/**
	 * 获取服务器列表
	 * 
	 * @throws InterruptedException
	 * @throws KeeperException
	 */
	private void getServerList() throws KeeperException, InterruptedException {
		List<String> children = zkClient.getChildren("/servers", true);
		// 存储服务器列表
		ArrayList<String> serverList = new ArrayList<>();
		// 获取每个节点的数据
		for (String chird : children) {
			byte[] data = zkClient.getData("/servers/" + chird, false, null);
			serverList.add(new String(data));
		}

		System.out.println(serverList);
	}

	/**
	 * 获取连接zkServer
	 * 
	 * @throws IOException
	 */
	private void getConnection() throws IOException {
		zkClient = new ZooKeeper(connectString, sessionTimeout, new Watcher() {

			@Override
			public void process(WatchedEvent event) {
				List<String> children;
				try {
					getServerList();
				} catch (KeeperException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
}
