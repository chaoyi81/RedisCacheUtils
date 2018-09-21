package com.java.test.cache;

import redis.clients.jedis.JedisCluster;

public class TestCluster {

	public static void main(String[] args) {
		MyThread myThread = new MyThread();
		new Thread(myThread).start();;
		new Thread(myThread).start();;
		new Thread(myThread).start();;
		new Thread(myThread).start();;
		new Thread(myThread).start();;
		new Thread(myThread).start();;

	}

}
class MyThread extends Thread{
	@Override
	public void run() {
		JedisCluster jedisCluster =	RedisClusterManager.getJedisCluster();
		//jedisCluster.set("test", "cluster");
		//System.out.println(jedisCluster.get("test"));
		System.out.println(jedisCluster.hashCode());
	}
}