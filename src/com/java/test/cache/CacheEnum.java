package com.java.test.cache;

public enum CacheEnum {

	SYYMEMCACHED("spymemcached"), XMEMCACHED("xmemcached"), REDIS("redis");

	public String value;

	CacheEnum(String str) {
		this.value = str;
	}

	public String getValue() {
		return value;
	}
}
