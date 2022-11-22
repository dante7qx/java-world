package com.dante.juc;

import java.time.LocalTime;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

import cn.hutool.core.thread.ThreadUtil;

/**
 * LockSupport是一个工具类，提供了基本的线程阻塞和唤醒功能，它是创建锁和其他同步组件的基础工具，内部是使用sun.misc.Unsafe类实现的。
 *	LockSupport和使用它的线程都会关联一个许可，park方法表示消耗一个许可，调用park方法时，如果许可可用则park方法返回，如果没有许可则一直阻塞直到许可可用。
 *	unpark方法表示增加一个许可，多次调用并不会积累许可，因为许可数最大值为1。
 * 
	park()： 阻塞当前线程，直到unpark方法被调用或当前线程被中断，park方法才会返回。
	park(Object blocker)： 同park()方法，多了一个阻塞对象blocker参数。
	parkNanos(long nanos)： 同park方法，nanos表示最长阻塞超时时间，超时后park方法将自动返回。
	parkNanos(Object blocker, long nanos)： 同parkNanos(long nanos)方法，多了一个阻塞对象blocker参数。
	parkUntil(long deadline)： 同park()方法，deadline参数表示最长阻塞到某一个时间点，当到达这个时间点，park方法将自动返回。（该时间为从1970年到现在某一个时间点的毫秒数）
	parkUntil(Object blocker, long deadline)： 同parkUntil(long deadline)方法，多了一个阻塞对象blocker参数。
	unpark(Thread thread)： 唤醒处于阻塞状态的线程thread。（许可最大值为1）
 * 
	和显式锁、隐式锁等待唤醒的区别
	1. park和unpark方法的调用不需要获取锁。
	2. 先调用unpark方法后调用park方法依然可以唤醒。
	3. park方法响应中断，线程被中断后park方法直接返回，但是不会抛InterruptedException异常。
	4. unpark方法是直接唤醒指定的线程。
 * 
 * 
 * 
 * 参考资料：https://www.cnblogs.com/seve/p/14555740.html
 */
public class LockSupportTest {
	
	public static void main(String[] args) {
		LockSupportTest test = new LockSupportTest();
		System.out.println("========================> Lock1");
		test.lock1();
		
		ThreadUtil.sleep(5, TimeUnit.SECONDS);
		
		System.out.println("========================> Lock2");
		test.lock2();
	}
	
	private void lock1() {
		Thread thread = new Thread(() -> {
            System.out.println("开始执行业务 - " + LocalTime.now());
            LockSupport.park();
            System.out.println("业务执行结束 - " + LocalTime.now());
        });
		
		thread.start();
        ThreadUtil.sleep(3, TimeUnit.SECONDS);

        System.out.println("给子线程thread增加一个许可");
        LockSupport.unpark(thread);
	}
	
	/**
	 * 先unpark增加许可，后park消费许可也是可以的。unpark会给thread增加一个许可，此时调用park方法，由于许可是可用的，所以park方法直接返回了。
	 */
	private void lock2() {
		Thread thread = new Thread(() -> {
			System.out.println("开始执行业务 - " + LocalTime.now());
            System.out.println("子线程thread给自己增加一个许可");
            LockSupport.unpark(Thread.currentThread());
            LockSupport.park();
            System.out.println("业务执行结束 - " + LocalTime.now());
        });

        thread.start();
	}
	
}
