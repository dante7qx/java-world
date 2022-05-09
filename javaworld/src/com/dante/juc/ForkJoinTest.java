package com.dante.juc;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

public class ForkJoinTest {

	public static void main(String[] args) {
		ForkJoinPool forkJoinPool = new ForkJoinPool(4);
		
		// 计算 10 亿项，分割任务的临界值为 1 千万
//		PiEstimateTask task = new PiEstimateTask(0, 1_000_000_000, 10_000_000);
		PiEstimateTask task = new PiEstimateTask(0, 10000000, 100000);
		// 阻塞，直到任务执行完毕返回结果
//		double pi = forkJoinPool.invoke(task); 
		
		// 不阻塞
		Future<Double> future = forkJoinPool.submit(task); // 不阻塞
		double pi = 0;
		try {
			pi = future.get();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("π 的值：" + pi);
		System.out.println("future 指向的对象是 task 吗？" + (future == task));

		// 向线程池发送关闭的指令
		forkJoinPool.shutdown(); 
	}

}

/**
 * 计算 π = 4 * (1 - 1/3 + 1/5 - 1/7 + 1/9 - ……)
 * 
 * @author dante
 */
class PiEstimateTask extends RecursiveTask<Double> {
	private static final long serialVersionUID = 8233700631993393170L;
	private final long begin;
	private final long end;
	private final long threshold; // 分割任务的临界值

	public PiEstimateTask(long begin, long end, long threshold) {
		super();
		this.begin = begin;
		this.end = end;
		this.threshold = threshold;
	}

	@Override
	protected Double compute() {
		if (end - begin <= threshold) {
			int sign = 1; // 符号，取 1 或者 -1
			double result = 0.0;
			for (long i = begin; i < end; i++) {
				result += sign / (i * 2.0 + 1);
				sign = -sign;
			}
			return result * 4;
		}

		// 分割任务
		long middle = (begin + end) / 2;
		System.out.println(Thread.currentThread().getName() + " -> " + middle);
		PiEstimateTask leftTask = new PiEstimateTask(begin, middle, threshold);
		PiEstimateTask rightTask = new PiEstimateTask(middle, end, threshold);

		leftTask.fork(); // 异步执行 leftTask
		rightTask.fork(); // 异步执行 rightTask

		double leftResult = leftTask.join(); // 阻塞，直到 leftTask 执行完毕返回结果
		double rightResult = rightTask.join(); // 阻塞，直到 rightTask 执行完毕返回结果

		return leftResult + rightResult; // 合并结果
	}
}
