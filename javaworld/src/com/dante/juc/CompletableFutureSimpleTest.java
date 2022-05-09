package com.dante.juc;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * CompletionStage 代表异步计算中的一个阶段或步骤。 CompletionStage
 * 的接口一般都返回新的CompletionStage，表示执行完一些逻辑后，生成新的CompletionStage，构成链式的阶段型的操作。
 * 
 * 通过 CompletableFuture 实现等待某个线程完成之后才做后续的事
 * 
 * @author dante
 *
 */
public class CompletableFutureSimpleTest {

	public static void main(String[] args) throws InterruptedException {
		CompletableFuture<Double> futurePrice = getPriceAsync();
		// 当前线程不被阻塞
		System.out.println("主线程业务操作1...");
		// 线程任务完成的话，执行回调函数，不阻塞后续操作
		futurePrice.whenComplete((result, throwable) -> {
			System.out.println(result);
			System.out.println("执行基于结果的后续业务处理...");
		});
		System.out.println("主线程业务操作2...");

		TimeUnit.SECONDS.sleep(5L);
	}

	/**
	 * 模拟一个很耗时的操作，因为其行为是在一个线程中做的，所以不会阻塞主线程
	 * 
	 * @return
	 */
	static CompletableFuture<Double> getPriceAsync() {
		CompletableFuture<Double> futurePrice = CompletableFuture.supplyAsync(() -> {
			try {
				TimeUnit.SECONDS.sleep(2L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return Double.valueOf(23.55d);
		});
		return futurePrice;
	}
}
