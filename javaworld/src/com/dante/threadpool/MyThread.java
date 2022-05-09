package com.dante.threadpool;

public class MyThread implements Runnable {

	private String name;
	
	public MyThread(String name) {
		this.name = name;
	}

	@Override
	public void run() {
		try {
			if("Thread 2".equals(name)) {
				throw new Exception("测试错误数据");
			}
			System.out.println(this.name + "正在执行。。。。。。");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

}
