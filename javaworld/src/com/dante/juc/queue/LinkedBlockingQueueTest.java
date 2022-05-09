package com.dante.juc.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 一个线程安全的、基于数组、有界的、阻塞的、FIFO 队列。
 * 1. 试图向已满队列中放入元素会导致操作受阻塞；
 * 2. 试图从空队列中提取元素将导致类似阻塞。
 * 
 * 支持公平性选择。
 * 1. 通过将公平性 (fairness) 设置为 true 而构造的队列允许按照 FIFO 顺序访问线程。
 * 2. 公平性通常会降低吞吐量，但也减少了可变性和避免了“不平衡性”
 * 
 * @author dante
 *
 */
public class LinkedBlockingQueueTest {
	private static final BlockingQueue<String> queue = new LinkedBlockingQueue<>(2);
	
	public static void main(String[] args) throws InterruptedException {
		new Thread(new Productor(queue, "LinkedBlockingQueue")).start();
		Thread.sleep(4000L);
		new Thread(new Consumer(queue)).start();
		new Thread(new Consumer(queue)).start();
		Thread.sleep(4000L);
	}
	
	
}
