package com.dante.juc.queue;

import java.util.concurrent.BlockingQueue;

/**
 * 生产者
 * 
 * @author dante
 *
 */
public class Productor implements Runnable {
	private final String queueName;
	private final BlockingQueue<String> queue;
	
	public Productor(BlockingQueue<String> queue, String queueName) {
		this.queue = queue;
		this.queueName = queueName;
	}
	
	@Override
	public void run() {
		try {
			queue.put(queueName + "1");
			Thread.sleep(1000L);
			queue.put(queueName + "2");
			Thread.sleep(1000L);
			queue.put(queueName + "3");
			
			Thread.sleep(5000L);
			
			for (int i = 4; i < 7; i++) {
				queue.put("LinkedBlockingQueue" + i);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
