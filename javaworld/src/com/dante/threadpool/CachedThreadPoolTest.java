package com.dante.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 创建一个可缓存的线程池。如果线程池的大小超过了处理任务所需要的线程，
 * 那么就会回收部分空闲（60秒不执行任务）的线程，当任务数增加时，此线程池又可以智能的添加新线程来处理任务。
 * 此线程池不会对线程池大小做限制，线程池大小完全依赖于操作系统（或者说JVM）能够创建的最大线程大小。
 * 
 * @author dante
 *
 */
public class CachedThreadPoolTest {

	public static void main(String[] args) {
		ExecutorService pool = Executors.newCachedThreadPool();
		
		Runnable t1 = new MyThread("Thread 1");
		Runnable t2 = new MyThread("Thread 2");
		Runnable t3 = new MyThread("Thread 3");
		Runnable t4 = new MyThread("Thread 4");
		Runnable t5 = new MyThread("Thread 5");
		
		pool.execute(t1);
		pool.execute(t2);
		pool.execute(t3);
		pool.execute(t4);
		pool.execute(t5);
		
		pool.shutdown();
	}
	
}
