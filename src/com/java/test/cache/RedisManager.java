package com.java.test.cache;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisManager extends CacheManager {

	private static JedisPool pool = null;
	private static final String HOST = "192.168.12.120"; // ip
	private static final int PORT = 6379; // 端口
	private static final String AUTH = "123456"; // 密码(原始默认是没有密码)
	private static int MAX_ACTIVE = 1024; // 最大连接数
	private static int MAX_IDLE = 200; // 设置最大空闲数
	private static int MAX_WAIT = 10000; // 最大连接时间
	private static int TIMEOUT = 10000; // 超时时间
	private static boolean BORROW = true; // 在borrow一个事例时是否提前进行validate操作

	static {
		GenericObjectPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(MAX_ACTIVE);
		config.setMaxIdle(MAX_IDLE);
		config.setMaxWaitMillis(MAX_WAIT);
		config.setTestOnBorrow(BORROW);

		pool = new JedisPool(config, HOST, PORT,TIMEOUT,AUTH);
//		pool = new JedisPool(config, HOST, PORT);
	}

	public static synchronized Jedis getJedis() {
		try {
			if (pool != null) {
				return pool.getResource();
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	//
	public static void disableTime(String key, int seconds) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			if (jedis != null) {
				jedis.expire(key, seconds);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			getColse(jedis);
		}
	}

	public static boolean addObject(String key, Object obj) {
		Jedis jedis = null;
		String value = com.alibaba.fastjson.JSONObject.toJSONString(obj);
		try {
			jedis = getJedis();
			String code = jedis.set(key, value);
			if (code.equals("ok")) {
				return true;
			}
		} catch (Exception e) {
			return false;
		} finally {
			getColse(jedis);
		}
		return false;
	}

	public static boolean addValue(String key, String value) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			String code = jedis.set(key, value);
			if (code.equals("ok")) {
				return true;
			}
		} catch (Exception e) {
			return false;
		} finally {
			getColse(jedis);
		}
		return false;
	}

	public static boolean delKey(String key) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			Long code = jedis.del(key);
			if (code > 1) {
				return true;
			}
		} catch (Exception e) {
			return false;
		} finally {
			getColse(jedis);
		}
		return false;
	}

	public static void getColse(Jedis jedis) {
		try {
			if (jedis != null) {
				jedis.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Object get(String key) {
		return getJedis().get(key);
	}

	@Override
	public String getName() {
		return CacheEnum.REDIS.getValue();
	}

	public static void main(String[] args) {
//		Jedis jedis = new Jedis("192.168.12.120", 6379);
//		System.out.println("connetion ok:" + jedis.ping());
//		// System.out.println(jedis.asking());
//		jedis.set("runkey", "www.run.com");
//		System.out.println(jedis.get("runkey"));
//		System.out.println("---------------------");
//		//
//		jedis.lpush("site", "google");
//		jedis.lpush("site", "baidu");
//		jedis.lpush("site", "ali");
//		List<String> lrange = jedis.lrange("site", 0, 2);
//		for (int i = 0; i < lrange.size(); i++) {
//			System.out.println(lrange.get(i));
//		}
//		System.out.println("---------------------");
//		//
//		Set<String> keys = jedis.keys("*");
//		Iterator<String> iterator = keys.iterator();
//		while (iterator.hasNext()) {
//			System.out.println(iterator.next());
//		}
		System.out.println(RedisManager.get("runkey"));
//		System.out.println(RedisManager.addValue("mykey", "www.run.com"));
		
		for (int i = 0; i < 1000; i++) {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					System.out.println(RedisManager.get("mykey"));
				}
			}).start();
		}
		for (int i = 0; i < 1000; i++) {
			
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					RedisManager.addValue("mykey",Math.abs( Math.random()*100)+"");
				}
			}).start();
		}
		
	}
}
