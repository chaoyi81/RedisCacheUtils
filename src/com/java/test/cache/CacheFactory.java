package com.java.test.cache;

public interface CacheFactory {
	CacheManager createCacheManger(CacheType key);
}
