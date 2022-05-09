package com.dante.juc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * AtomicInteger这个类的存在是为了满足在高并发的情况下,原生的整形数值自增线程不安全的问题 
 * 
 * 高并发: 1000个线程起
 * 
 * @author dante
 *
 */
public class AtomicIntegerTest {
	
	private static final AtomicInteger atomicInteger = new AtomicInteger(0);
	private static int intVal = 0;
	
	private final static int THREAD_COUNT = 1000;
	
	/**
	 * 使用 AtomicInteger 在高并发环境下的操作
	 */
	private static void atomicIntegerIncrement() {
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
        for (int i = 0; i < 10000; i++) {
            executorService.execute(() -> {
                for (int j = 0; j < 4; j++) {
                	atomicInteger.getAndIncrement();
                }
            });
        }
        executorService.shutdown();
    }
	
	/**
	 * 使用 int 在高并发环境下的操作
	 */
	private static void intIncrement() {
		ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
		for (int i = 0; i < 10000; i++) {
            executorService.execute(() -> {
                for (int j = 0; j < 4; j++) {
                	intVal++;
                }
            });
        }
        executorService.shutdown();
	}
	
	public static void main(String[] args) throws InterruptedException {
		atomicIntegerIncrement();
		Thread.sleep(5000L);
		System.out.println("AtomicInteger 执行结果: " + atomicInteger.get());
		
		intIncrement();
		Thread.sleep(5000L);
		System.out.println("int 执行结果: " + intVal);
	}
}
