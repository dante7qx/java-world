package com.dante.juc;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * Callable和Future，通过它们可以在任务执行完毕之后得到任务执行结果。
 * 
 * Future: 就是对于具体的Runnable或者Callable任务的执行结果进行取消、查询是否完成、获取结果。
 * 必要时可以通过get方法获取执行结果，该方法会阻塞直到任务返回结果
 * 
 * 设计模式：去除了主函数的等待时间，并使得原本需要等待的时间段可以用于处理其他业务逻辑
 * 
 * @author dante
 *
 */
public class FutureTest {

	public static void main(String[] args) {
		ExecutorService executor = Executors.newCachedThreadPool();
		Future<String> future = executor.submit(new MyCallable(2));

		FutureTask<String> futureTask = new FutureTask<String>(
				new MyCallable(3));
		executor.submit(futureTask);
		executor.shutdown();

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		System.out.println("主线程在执行任务");

		try {
			System.out.println("Future运行结果：" + future.get());
			System.out.println("FutureTask运行结果：" + futureTask.get());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

		System.out.println("所有任务执行完毕");
	}

}

/**
 * call()函数返回的类型就是传递进来的V类型
 * 
 * @author dante
 *
 */
class MyCallable implements Callable<String> {
	private int calculateTime;

	public MyCallable(int calculateTime) {
		this.calculateTime = calculateTime * 1000;
	}

	@Override
	public String call() throws Exception {
		System.out.println("子线程在进行计算");
		Thread.sleep(calculateTime);

		return "睡眠" + calculateTime + "秒！";
	}
}