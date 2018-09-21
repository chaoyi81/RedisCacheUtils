package com.java.test.cache;

public class CacheFactoryImpl implements CacheFactory {

	// private static CacheFactoryImpl instance = null;
	//
	// private CacheFactoryImpl() {
	// if (instance != null) {
	// throw new RuntimeException();
	// }
	// }
	//
	// public static synchronized CacheFactory getInstance() {
	// if (instance == null) {
	// instance = new CacheFactoryImpl();
	// }
	// return instance;
	// }
//	private static CacheFactoryImpl instance = null;
//
//	private CacheFactoryImpl() {
//		if (instance != null) {
//			throw new RuntimeException();
//		}
//	}
//
//	public static CacheFactory getInstance() {
//		if (instance == null) {
//			synchronized (CacheFactoryImpl.class) {
//				if (instance==null) {					
//					instance = new CacheFactoryImpl();
//				}
//			}
//		}
//		return instance;
//	}

	public static class SingletonHandler{
		private static CacheFactoryImpl instance = new CacheFactoryImpl();
	}
	public static CacheFactory getInstance() {
		return SingletonHandler.instance;
	}
	
	// @Override
	// public CacheManager createCacheManger(String key) {
	// CacheManager cacheManager = null;
	// switch (key) {
	// case "redis":
	// cacheManager = new RedisManager();
	// break;
	// case "xmemcached":
	// cacheManager = new Xmemcached();
	// break;
	// }
	// return cacheManager;
	// }

	public static void main(String[] args) {
		System.out.println(CacheEnum.REDIS.getValue());
		System.out.println(CacheEnum.XMEMCACHED.getValue());
		CacheFactory cacheFactory = CacheFactoryImpl.getInstance();
		// System.out.println(cacheFactory.createCacheManger("redis").getName());
		System.out.println(cacheFactory.createCacheManger(CacheType.REDIS).getName());
	}

	public CacheManager createCacheManger(CacheType key) {
		CacheManager cacheManager = null;
		switch (key) {
		case REDIS:
			cacheManager = new RedisManager();
			break;
		case XMEMCACHED:
			cacheManager = new Xmemcached();
			break;
		case SYYMEMCACHED:
			cacheManager = new SpyMemcached();
			break;
		}
		return cacheManager;
	}
}
