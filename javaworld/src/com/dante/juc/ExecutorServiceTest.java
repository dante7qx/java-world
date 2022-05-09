package com.dante.juc;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


public class ExecutorServiceTest {
	
	public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
		ExecutorService executorService = Executors.newCachedThreadPool();
		Future<String> result = executorService.submit(new Callable<String>() {
			@Override
			public String call() throws Exception {
				Thread.sleep(1000L);
				return new Random().nextInt(100) + "";
			}
		});
		
		System.out.println(111);
		System.out.println(222);
		System.out.println(result.get());
		System.out.println(result.isDone());
		
		executorService.shutdown();
		
		// 传统线程
		Runnable task = () -> System.out.println("I am a task, " + Thread.currentThread().getName());
		Thread thread = new Thread(task);
		thread.start();
		
		// 并发框架
		ExecutorService executorService1 = Executors.newCachedThreadPool();
		for (int i = 0; i < 5; i++) {
			executorService1.execute(task);
		}
		executorService1.shutdown();
		
		// Callable
		Callable<String> callTask = () -> {
			Thread.sleep(300L);
			return "I am a callable task, " + Thread.currentThread().getName();
		};
		
		Callable<String> callExceptionTask = () -> {
			throw new IllegalStateException("I am a callable exception task, " + Thread.currentThread().getName());
		};
		
		// CallableTask
		ExecutorService executorService2 = Executors.newFixedThreadPool(3);
		try {
			for (int i = 0; i < 5; i++) {
				Future<String> future = executorService2.submit(callTask);
				System.out.println("result is:");
				System.out.println(future.get(350, TimeUnit.MILLISECONDS));
			}
			Future<String> exceptionFuture = executorService2.submit(callExceptionTask);
			System.out.println(exceptionFuture.get());
		} catch (ExecutionException e) {
			System.out.println(e.getMessage());
		} finally {
			executorService2.shutdown();
		}
		
		// FutureTask 
		ExecutorService executorService3 = Executors.newSingleThreadExecutor();
		FutureTask<String> ft = new FutureTask<String>(callTask);
		try {
			executorService3.submit(ft);
			System.out.println(ft.get());
		} finally {
			executorService3.shutdown();
		}
	}
	
}
