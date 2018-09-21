package com.java.test.cache;

public class Main {

	public static void main(String[] args) {
		CacheFactory cacheFactory = null;
		long start =System.currentTimeMillis();
		for (int i = 0; i < 10000; i++) {
			cacheFactory = CacheFactoryImpl.getInstance();
			System.out.println(cacheFactory.hashCode());
		}
		long end = System.currentTimeMillis();
		System.out.println((end-start)+"ms");
	}

}
