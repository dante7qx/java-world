package com.dante.juc;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 一个 Lock 对象和一个 synchronized 代码块之间的主要不同点是：
 *		synchronized 代码块不能够保证进入访问等待的线程的先后顺序。
 *		你不能够传递任何参数给一个 synchronized 代码块的入口。因此，对于 synchronized 代码块的访问等待设置超时时间是不可能的事情。
 *		synchronized 块必须被完整地包含在单个方法里。而一个 Lock 对象可以把它的 lock() 和 unlock() 方法的调用放在不同的方法里。
 * 
 * @author dante
 *
 */
public class LockTest {
	// ReentrantLock: 独占锁，即一次只能有一个线程持有锁
	private Lock lock = new ReentrantLock();
	
	public static void main(String[] args) {
		final LockTest demo = new LockTest();
		for (int i = 0; i < 20; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					demo.run(Thread.currentThread().getName());
					
				}
			}).start();
		}
	}
	
	public void run(String curThread) {
		if(lock.tryLock()) {
			try {
				System.out.println(curThread + "开始执行。");
				Thread.sleep((long) Math.random() * 100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				lock.unlock();
			}
		} else {
			System.out.println("当前任务正在运行！");
		}
	}
	
}
