package com.java.test.cache;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

public class RedisClusterManager extends CacheManager {

	
	private static int MAX_ACTIVE = 1024; // 最大连接数
	private static int MAX_IDLE = 200; // 设置最大空闲数
	private static int MAX_WAIT = 10000; // 最大连接时间
	private static boolean BORROW = true; // 在borrow一个事例时是否提前进行validate操作
	private RedisClusterManager() {}
	private static class JedisClusterHandler{
		private static JedisCluster jedisCluster = null;
	}
	static {
		GenericObjectPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(MAX_ACTIVE);
		config.setMaxIdle(MAX_IDLE);
		config.setMaxWaitMillis(MAX_WAIT);
		config.setTestOnBorrow(BORROW);
		Set<HostAndPort> nodes = new HashSet<>();
		HostAndPort node1 = new HostAndPort("192.168.12.121", 3301);
		HostAndPort node2 = new HostAndPort("192.168.12.121", 3302);
		HostAndPort node3 = new HostAndPort("192.168.12.121", 3303);
		nodes.add(node1);
		nodes.add(node2);
		nodes.add(node3);
		JedisClusterHandler.jedisCluster = new JedisCluster(nodes, config);
	}

	@Override
	public String getName() {
		return "rediscluster";
	}

	public static JedisCluster getJedisCluster() {
		return JedisClusterHandler.jedisCluster;
	}
	/**
	 * 设置String数据类型
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public String set(String key, String value) {
		return getJedisCluster().set(key, value);
	}

	/**
	 * 获取String数据类型
	 * 
	 * @param key
	 * @return
	 */
	
	public String get(String key) {
		return getJedisCluster().get(key);
	}

	/**
	 * 设置hash数据类型
	 * 
	 * @param key
	 * @param item
	 * @param value
	 * @return
	 */
	
	public Long hset(String key, String item, String value) {
		return getJedisCluster().hset(key, item, value);
	}

	/**
	 * 获取hash数据类型
	 * 
	 * @param key
	 * @param item
	 * @return
	 */
	
	public String hget(String key, String item) {
		return getJedisCluster().hget(key, item);
	}

	/**
	 * 删除hash数据
	 * 
	 * @param key
	 * @param item
	 * @return
	 */
	
	public Long incr(String key) {
		return getJedisCluster().incr(key);
	}

	/**
	 * 加一操作
	 * 
	 * @param key
	 * @return
	 */
	
	public Long decr(String key) {
		return getJedisCluster().decr(key);
	}

	/**
	 * 减一操作
	 * 
	 * @param key
	 * @return
	 */
	
	public Long expire(String key, int second) {
		return getJedisCluster().expire(key, second);
	}

	/**
	 * 设置key的过期时间
	 * 
	 * @param key
	 * @param second
	 * @return
	 */
	
	public Long ttl(String key) {
		return getJedisCluster().ttl(key);
	}

	/**
	 * 判断key是否过期
	 * 
	 * @param key
	 * @return
	 */
	
	public Long hdel(String key, String item) {
		return getJedisCluster().hdel(key, item);
	}

}
