package com.java.test.cache;

import java.io.IOException;
import java.util.Properties;

public class PropertiesUtils {

	private static Properties properties;
	static{
		properties= new Properties();
		try {
			properties.load(PropertiesUtils.class.getClassLoader().getResourceAsStream("cache.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static String getValue(String key) {
		return properties.getProperty(key);
	}
}
