package com.atguigu.zk;

import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

public class ZkServer {

	private String connectString = "192.168.2.130:2181,192.168.2.131:2181,192.168.2.132:2181";
	private int sessionTimeout = 2000;

	private ZooKeeper zkClient = null;
	private String parentNode = "/servers";
	
	public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
		ZkServer zkServer = new ZkServer();
		
		//1、获取连接zkServer
		zkServer.getConnection();
		//2、注册服务器节点信息
		zkServer.regist(args[0]);
		//3、业务逻辑处理
		zkServer.business(args[0]);
	}

	/**
	 * 业务逻辑处理
	 * @param hostName
	 * @throws InterruptedException 
	 */
	private void business(String hostName) throws InterruptedException {
		System.out.println(hostName + " is online!");
		Thread.sleep(Long.MAX_VALUE);
	}

	//注册服务器节点信息
	private void regist(String hostName) throws KeeperException, InterruptedException {
		String nodeUrl = zkClient.create(parentNode + "/server", hostName.getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
		System.out.println("注册的节点： " + nodeUrl);
	}

	/**
	 * 获取连接zkServer
	 * @throws IOException 
	 */
	private void getConnection() throws IOException {
		zkClient = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
			
			@Override
			public void process(WatchedEvent event) {
				// TODO Auto-generated method stub
				
			}
		});
	}
}
