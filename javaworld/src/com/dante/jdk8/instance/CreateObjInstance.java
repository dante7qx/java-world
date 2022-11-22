package com.dante.jdk8.instance;

public class CreateObjInstance {
	
	private String name = "不适用 new 创建对象实例";
	
	public void print() {
		System.out.println(name);
	}
	
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		Class<?> clazz1;
		try {
			clazz1 = Class.forName("com.dante.jdk8.instance.CreateObjInstance");
			CreateObjInstance obj1 = (CreateObjInstance) clazz1.newInstance();
			obj1.print();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
	}
	
}
