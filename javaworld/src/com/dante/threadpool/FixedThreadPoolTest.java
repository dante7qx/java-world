package com.dante.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 创建固定大小的线程池。每次提交一个任务就创建一个线程，直到线程达到线程池的最大大小。
 * 线程池的大小一旦达到最大值就会保持不变，如果某个线程因为执行异常而结束，那么线程池会补充一个新线程。
 * 
 * @author dante
 */
public class FixedThreadPoolTest {

	public static void main(String[] args) throws InterruptedException {
		ExecutorService pool = Executors.newFixedThreadPool(3);
		
		Runnable t1 = new MyThread("Thread 1");
		Runnable t2 = new MyThread("Thread 2");
		Runnable t3 = new MyThread("Thread 3");
		Runnable t4 = new MyThread("Thread 4");
		Runnable t5 = new MyThread("Thread 5");
		
		pool.execute(t1);
		pool.execute(t2);
		pool.execute(t3);
		
		Thread.sleep(5000);
		
		pool.execute(t4);
		pool.execute(t5);
		
	}
	
}
