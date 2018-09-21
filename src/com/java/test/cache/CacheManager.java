package com.java.test.cache;

public abstract class CacheManager {
	 public static void disableTime(String key,int seconds){
		 
	 }
	 public static boolean addObject(String key,Object obj){
		 return true;
	 }
	 public static boolean addValue(String key,String value){
		 return true;
	 }
	 public static boolean delKey(String key){
		 return true;
	 }
	//
	String getName() {
		return "CacheManager";
	}
}
