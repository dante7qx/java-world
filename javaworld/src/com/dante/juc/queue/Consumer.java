package com.dante.juc.queue;

import java.util.concurrent.BlockingQueue;

public class Consumer implements Runnable {

	private final BlockingQueue<String> queue;
	
	public Consumer(BlockingQueue<String> queue) {
		this.queue = queue;
	}
	
	@Override
	public void run() {
		long currentThreadId = Thread.currentThread().getId();
		System.out.println("当前线程" + currentThreadId + ", 队列长度 " + queue.size());
		try {
			System.out.println("[" + currentThreadId + "] 获取消息1 ===> " + queue.take());
			System.out.println("[" + currentThreadId + "] 获取消息2 ===> " + queue.take());
			System.out.println("[" + currentThreadId + "] 获取消息2 ===> " + queue.take());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
