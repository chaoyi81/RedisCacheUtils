package com.java.test.cache;

public class StaticClass {
	public static Object object = new Object();
	static{
		System.out.println("StaticClass.static_method()");
	}
	{
		System.out.println("StaticClass.ss_method()");
	}
	public static Object getObject() {
		return object;
	}
	public static Object getObject2() {
		return new Object();
	}
	public static void main(String[] args) {
		StaticClass staticClass = new StaticClass();
		System.out.println(staticClass.getObject().hashCode());
		System.out.println(staticClass.getObject2().hashCode());
		System.out.println("------------------------------");
		StaticClass staticClass2 = new StaticClass();
		System.out.println(staticClass2.getObject().hashCode());
		System.out.println(staticClass2.getObject2().hashCode());
	}

}
