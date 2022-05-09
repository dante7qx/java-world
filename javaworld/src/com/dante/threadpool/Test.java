package com.dante.threadpool;

public class Test {
	
	public static void main(String[] args) {
		Runnable t1 = new MyThread("Thread 1");
		Runnable t2 = new MyThread("Thread 2");
		Runnable t3 = new MyThread("Thread 3");
		Runnable t4 = new MyThread("Thread 4");
		Runnable t5 = new MyThread("Thread 5");
		
		ThreadPoolUtil.run(t1);
		ThreadPoolUtil.run(t2);
		ThreadPoolUtil.run(t3);
		ThreadPoolUtil.run(t4);
		ThreadPoolUtil.run(t5);
	}
	
}
