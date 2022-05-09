package com.dante.juc;

import java.util.Date;
import java.util.concurrent.CyclicBarrier;

/**
 * 类是一种同步机制，它能够对处理一些算法的线程实现同步
 * 
 * CyclicBarrier的执行顺序：
 * 		当少于期望数量的线程到达barrier.await()方法时，线程停止。
 * 		当达到期望的数量的时候，执行CyclicBarrier中的runnable方法，cyclic解除，其他的线程接着开始执行
 * 
 * 满足以下任何条件都可以让等待 CyclicBarrier 的线程释放：
 *		最后一个线程也到达 CyclicBarrier(调用 await())
 *		当前线程被其他线程打断(其他线程调用了这个线程的 interrupt() 方法)
 *		其他等待栅栏的线程被打断
 *		其他等待栅栏的线程因超时而被释放
 *		外部线程调用了栅栏的 CyclicBarrier.reset() 方法
 * 
 * 一般小黑车是坐4个人，只有4个人都满了之后小黑车才会走， 那么如果你是第一个来的，进车坐下，等第二个人来。 第二个人来了，等第三个人，
 * 如此直到所有的4个人满了之后小黑车才开车。
 * 
 * @author dante
 *
 */
public class CyclicBarrierTest {
	private final static CyclicBarrier carBarrier = new CyclicBarrier(4, new Runnable() {
		@Override
		public void run() {
			System.out.println("人数凑够了，开车！");

		}
	});

	public static void main(String[] args) {
		new Thread(new Pax("展容", carBarrier)).start();
		new Thread(new Pax("一飞", carBarrier)).start();
		new Thread(new Pax("但丁", carBarrier)).start();
		new Thread(new Pax("小小", carBarrier)).start();
	}
}

/**
 * 乘客
 * 
 * @author dante
 *
 */
class Pax implements Runnable {
	private String name;
	private CyclicBarrier barrier;

	public Pax(String name, CyclicBarrier barrier) {
		this.name = name;
		this.barrier = barrier;
	}

	@Override
	public void run() {
		try {
			Thread.sleep((long) (Math.random() * 10000));
			System.out.println(new Date() + "，乘客" + this.name + "进入车中休息，等待坐满后开车！");
			// 最多等待5秒，车门被踹开（barrier被释放）
//			barrier.await(5, TimeUnit.SECONDS); 
			barrier.await();
		} catch (Exception e) {
			System.out.println(this.name + "道：TM要等这么久，老子不坐了！");
		}
		System.out.println(new Date() + "，乘客" + this.name + "醒来了！");
	}

}
