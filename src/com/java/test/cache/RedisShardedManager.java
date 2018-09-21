package com.java.test.cache;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import redis.clients.jedis.Client;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

public class RedisShardedManager extends CacheManager {

	private static ShardedJedisPool pool = null;

	private static final String AUTH = ""; // ����(ԭʼĬ����û������)
	private static int MAX_ACTIVE = 1024; // ���������
	private static int MAX_IDLE = 200; // ������������
	private static int MAX_WAIT = 10000; // �������ʱ��
	private static int TIMEOUT = 10000; // ��ʱʱ��
	private static boolean BORROW = true; // ��borrowһ������ʱ�Ƿ���ǰ����validate����
	static List<JedisShardInfo> shards;
	static {
		GenericObjectPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(MAX_ACTIVE);
		config.setMaxIdle(MAX_IDLE);
		config.setMaxWaitMillis(MAX_WAIT);
		config.setTestOnBorrow(BORROW);
		JedisShardInfo shardInfo1 = new JedisShardInfo("192.168.12.121", 6379);
		JedisShardInfo shardInfo2 = new JedisShardInfo("192.168.12.120", 6379);
		shards = Arrays.asList(shardInfo1, shardInfo2);
		pool = new ShardedJedisPool(config, shards);
	}

	public static synchronized ShardedJedis getJedis() {
		if (pool == null) {
			return null;
		} else {
			return pool.getResource();
		}
	}

	@Override
	public String getName() {
		return "ShardedJedis";
	}

	public static void main(String[] args) {
		ShardedJedis jedis = getJedis();
		jedis.set("cnblog", "cnblog");
		jedis.set("redis", "redis");
		jedis.set("test", "test");
		jedis.set("123456", "1234567");
		Client client1 = jedis.getShard("cnblog").getClient();
		Client client2 = jedis.getShard("redis").getClient();
		Client client3 = jedis.getShard("test").getClient();
		Client client4 = jedis.getShard("123456").getClient();

		//// ��ӡkey���ĸ�server��
		System.out.println("cnblog in server:" + client1.getHost() + " and port is:" + client1.getPort());
		System.out.println("redis  in server:" + client2.getHost() + " and port is:" + client2.getPort());
		System.out.println("test   in server:" + client3.getHost() + " and port is:" + client3.getPort());
		System.out.println("123456 in server:" + client4.getHost() + " and port is:" + client4.getPort());
	}
}
