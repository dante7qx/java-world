package com.dante.juc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

/**
 * 一个同步辅助类，在完成一组正在其他线程中执行的操作之前，它允许一个或多个线程一直等待。
 *	主要方法
 *		public CountDownLatch(int count);				// 参数count为计数值
 *		public void countDown();							// 将count值减1
 *		public void await() throws InterruptedException // 调用await()方法的线程会被挂起，它会等待直到count值为0才继续执行
 * 
 * @author dante
 *
 */
public class CountDownLatchTest {
	public static void main(String[] args) throws InterruptedException {
		// 7个工人一起工作
		CountDownLatch latch = new CountDownLatch(7);
		Thread worker1 = new Thread(new Worker("小小", 7, latch));
		Thread worker2 = new Thread(new Worker("但丁", 6, latch));
		Thread worker3 = new Thread(new Worker("小宁", 2, latch));
		Thread worker4 = new Thread(new Worker("小晴晴", 4, latch));
		Thread worker5 = new Thread(new Worker("一飞", 9, latch));
		Thread worker6 = new Thread(new Worker("新新", 3, latch));
		Thread worker7 = new Thread(new Worker("小灼", 5, latch));

		System.out.println("======== 7人 " + new Date() + "开始协同工作！========");
		worker1.start();
		worker2.start();
		worker3.start();
		worker4.start();
		worker5.start();
		worker6.start();
		worker7.start();
		latch.await();
		System.out.println("======== 7人 " + new Date() + "协同工作完成！========");
	}

}

class Worker implements Runnable {
	private String name;
	private int workTime;
	private CountDownLatch latch;

	public Worker(String name, int workTime, CountDownLatch latch) {
		this.name = name + " ";
		this.workTime = workTime;
		this.latch = latch;
	}

	@Override
	public void run() {
		try {
			System.out.println("💪 " + this.name + workTime() + " 开始工作了");
			Thread.sleep(this.workTime * 1000);
			System.out.println("🚬 " + this.name + workTime() + " 完成工作了");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 每个线程退出前必须调用 countDown 方法，线程执行代码注意 catch 异常，确保 countDown 方法被执行到，
			// 避免主线程无法执行 至 await 方法，直到超时才返回结果
			this.latch.countDown();
		}
	}

	private String workTime() {
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-DD HH:mm:ss"));
	}
}
