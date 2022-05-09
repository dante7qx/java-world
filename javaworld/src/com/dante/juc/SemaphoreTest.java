package com.dante.juc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 信号量(Semaphore)，有时被称为信号灯，是在多线程环境下使用的一种设施, 它负责协调各个线程, 以保证它们能够正确、合理的使用公共资源。
 * Semaphore分为单值和多值两种，前者只能被一个线程获得，后者可以被若干个线程获得。
 * 
 * 计数信号量由一个指定数量的 "许可" 初始化。每调用一次 acquire()，一个许可会被调用线程取走。每调用一次
 * release()，一个许可会被返还给信号量。 因此，在没有任何 release() 调用时，最多有 N 个线程能够通过 acquire() 方法，N
 * 是该信号量初始化时的许可的指定数量。这些许可只是一个简单的计数器。
 * 
 * 信号量是否采用公平模式: Semaphore(int permits, boolean fair)
 * 如果以公平方式执行，则线程将会按到达的顺序（FIFO）执行。 如果是非公平，则可以后请求的有可能排在队列的头部。
 * 
 * @author dante
 *
 */
public class SemaphoreTest {

	public static void main(String[] args) {
		ExecutorService exec = Executors.newCachedThreadPool();
		// 10个车位
		final Semaphore semp = new Semaphore(10, true);

		for (int i = 1; i <= 50; i++) {
			exec.execute(new Car(i, semp));
		}

		exec.shutdown();
	}
}

class Car implements Runnable {
	private int carNum; // 车牌号
	private Semaphore semp; // 等待号

	Car(int carNum, Semaphore semp) {
		this.carNum = carNum;
		this.semp = semp;
	}

	@Override
	public void run() {
		try {
			semp.acquire();	// 获取许可
			System.out.println("豪车[" + carNum + "]获取许可，进入停车场停车！");
			Thread.sleep((long) Math.random() * 10000);
			semp.release(); // 访问完后，释放许可
			System.out.println("豪车[" + carNum + "]驶出停车场");
			System.out.println("当前停车场闲置车位数 【" + semp.availablePermits() + "】，等待进入的车辆数是【" + semp.getQueueLength() + "】");
			System.out.println("--------------------------------------------------------------------------------");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
