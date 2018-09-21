package com.java.test.cache;

public class Xmemcached extends CacheManager {



	@Override
	public String getName() {
		return CacheEnum.XMEMCACHED.getValue();
	}

}
