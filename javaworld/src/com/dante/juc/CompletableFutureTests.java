package com.dante.juc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

/**
 * CompletableFuture首先是一个Future，它拥有Future所有的功能，包括获取异步执行结果，取消正在执行的任务等。
 * 除此之外，CompletableFuture还是一个CompletionStage。
 * 什么是CompletionStage呢？
 * 在异步程序中，如果将每次的异步执行都看成是一个stage的话，我们通常很难控制异步程序的执行顺序，在javascript中，我们需要在回调中执行回调。这就会形成传说中的回调地狱。
 * 好在在ES6中引入了promise的概念，可以将回调中的回调转写为链式调用，从而大大的提升了程序的可读性和可写性。
 * 同样的在java中，我们使用CompletionStage来实现异步调用的链式操作。
 * CompletionStage定义了一系列的then*** 操作来实现这一功能。
 * 
 * （1）底层调用的是线程池去执行任务，而CompletableFuture中默认线程池为ForkJoinPool。ForkJoinPool线程池的大小取决于CPU的核数。
 * 		CPU密集型任务线程池大小配置为CPU核心数就可以了，但是IO密集型，线程池的大小由**CPU数量 * CPU利用率 * (1 + 线程等待时间/线程CPU时间)**确定。
 * 		而CompletableFuture的应用场景就是IO密集型任务，因此默认的ForkJoinPool一般无法达到最佳性能，我们需自己根据业务创建线程池
 * （2）异步意味着存在上下文切换，可能性能不一定比同步好。
 * 
 * 参考资料：
 * https://zhuanlan.zhihu.com/p/101716685
 * https://colobu.com/2018/03/12/20-Examples-of-Using-Java%E2%80%99s-CompletableFuture/
 * https://github.com/manouti/completablefuture-examples
 * 
 * @author dante
 *
 */
public class CompletableFutureTests {
	
	static Random random = new Random();
	
	/**
	 * 1. 创建一个完成的CompletableFuture
	 * 
	 * 使用一个预定义的结果创建一个完成的CompletableFuture,通常我们会在计算的开始阶段使用它
	 */
	@Test
	public void completedFutureExample() {
		CompletableFuture<String> cf = CompletableFuture.completedFuture("HelloWorld");
	    assertTrue(cf.isDone());
	    assertEquals("HelloWorld", cf.getNow(null));
	    System.out.println(cf.getNow(null));
	}
	
	/**
	 * 2. 异步阶段
	 * 
	 * CompletableFuture的方法如果以Async结尾，它会异步的执行(没有指定executor的情况下)， 异步执行通过ForkJoinPool实现， 
	 * 它使用守护线程去执行任务。注意这是CompletableFuture的特性， 其它CompletionStage可以override这个默认的行为
	 */
	@Test
	public void runAsyncExample() {
		CompletableFuture<Void> cf = CompletableFuture.runAsync(() -> {
	        assertTrue(Thread.currentThread().isDaemon());
	        randomSleep();
	    });
	    assertFalse(cf.isDone());
	    sleepEnough();
	    assertTrue(cf.isDone());
	}
	
	/**
	 * 3. 在前一个阶段上应用函数
	 * 
	 * CompletableFuture的返回结果为字符串message,然后应用一个函数把它变成大写字母
	 * （1）then意味着这个阶段的动作发生当前的阶段正常完成之后
	 * （2）Apply意味着返回的阶段将会对结果前一阶段的结果应用一个函数。
	 * （3）函数的执行会被阻塞
	 * 
	 */
	@Test
	public void thenApplyExample() {
		CompletableFuture<String> cf = CompletableFuture.completedFuture("message").thenApply(s -> {
			// 阻塞，非守护线程
	        assertFalse(Thread.currentThread().isDaemon());
	        return s.toUpperCase();
	    });
	    assertEquals("MESSAGE", cf.getNow(null));
	}
	
	/**
	 * 4. 在前一个阶段上异步应用函数
	 * 
	 * 通过调用异步方法(方法后边加Async后缀)，串联起来的CompletableFuture可以异步地执行（使用ForkJoinPool.commonPool()）
	 * 
	 */
	@Test
	public void thenApplyAsyncExample() {
		CompletableFuture <String>cf = CompletableFuture.completedFuture("message").thenApplyAsync(s -> {
			// 异步，守护线程
	        assertTrue(Thread.currentThread().isDaemon());
	        randomSleep();
	        return s.toUpperCase();
	    });
	    assertNull(cf.getNow(null));
	    assertEquals("MESSAGE", cf.join());
	}
	
	/**
	 * 5. 使用定制的Executor在前一个阶段上异步应用函数
	 * 
	 * 异步方法一个非常有用的特性就是能够提供一个Executor来异步地执行CompletableFuture。这个例子演示了如何使用一个固定大小的线程池来应用大写函数。
	 */
	@Test
	public void thenApplyAsyncWithExecutorExample() {
		CompletableFuture<String> cf = CompletableFuture.completedFuture("message").thenApplyAsync(s -> {
			System.out.println("当前线程 -> " + Thread.currentThread().getName());
	        assertTrue(Thread.currentThread().getName().startsWith("custom-executor-"));
	        // 指定Executor，使用线程池中的线程（非守护）
	        assertFalse(Thread.currentThread().isDaemon());
	        randomSleep();
	        return s.toUpperCase();
	    }, executor);
	 
	    assertNull(cf.getNow(null));
	    assertEquals("MESSAGE", cf.join());
	}
	
	/**
	 * 6. 消费前一阶段的结果
	 * 
	 * 如果下一阶段接收了当前阶段的结果，但是在计算的时候不需要返回值(它的返回类型是void)， 那么它可以不应用一个函数，而是一个消费者， 
	 * 调用方法也变成了thenAccept。本例中消费者同步地执行，所以我们不需要在CompletableFuture调用join方法。
	 */
	@Test
	public void thenAcceptExample() {
		StringBuilder result = new StringBuilder();
	    CompletableFuture.completedFuture("thenAccept message")
	            .thenAccept(s -> result.append(s));
	    System.out.println(result.toString());
	    assertTrue(result.length() > 0, "结果未空");
	}
	
	/**
	 * 7. 异步地消费迁移阶段的结果
	 * 
	 * 使用thenAcceptAsync方法， 串联的CompletableFuture可以异步地执行。
	 */
	@Test
	public void thenAcceptAsyncExample() {
		StringBuilder result = new StringBuilder();
	    CompletableFuture<Void> cf = CompletableFuture.completedFuture("thenAcceptAsync message")
	            .thenAcceptAsync(s -> result.append(s));
	    // 当结束时，返回结果
	    cf.join();
	    System.out.println(result.toString());
	    assertTrue(result.length() > 0, "结果未空");
	}
	
	/**
	 * 8. 完成计算异常（Java9+）
	 * 
	 * 异步操作如何显式地返回异常，用来指示计算失败。
	 * 这个例子，操作处理一个字符串，把它转换成答谢，我们模拟延迟一秒。
	 * 使用thenApplyAsync(Function, Executor)方法，第一个参数传入大写函数， executor是一个delayed executor,在执行前会延迟一秒。
	 * (1) 首先我们创建了一个CompletableFuture, 完成后返回一个字符串message, 接着我们调用thenApplyAsync方法，它返回一个CompletableFuture。
	 * 	   这个方法在第一个函数完成后，异步地应用转大写字母函数。通过delayedExecutor(timeout, timeUnit)延迟执行一个异步任务
	 * --> 创建了一个分离的handler阶段： exceptionHandler， 它处理异常异常，在异常情况下返回message upon cancel。<--
	 * （2）显式地用异常完成第二个阶段。 在阶段上调用join方法，它会执行大写转换，然后抛出CompletionException（正常的join会等待1秒，然后得到大写的字符串。不过我们的例子还没等它执行就完成了异常）， 
	 * 	   然后它触发了handler阶段。
	 */
	@Test
	public void completeExceptionallyExample() {
		CompletableFuture<String> cf = CompletableFuture.completedFuture("message").thenApplyAsync(String::toUpperCase,
	            CompletableFuture.delayedExecutor(1, TimeUnit.SECONDS));
	    CompletableFuture<String> exceptionHandler = cf.handle((s, th) -> { return (th != null) ? "message upon cancel" : ""; });
	    // 用异常完成第二个阶段
	    cf.completeExceptionally(new RuntimeException("completed exceptionally"));
	    assertTrue(cf.isCompletedExceptionally(), "Was not completed exceptionally");
	    try {
	        cf.join();
	        fail("Should have thrown an exception");
	    } catch(CompletionException ex) { // just for testing
	        assertEquals("completed exceptionally", ex.getCause().getMessage());
	        System.out.println(ex.getCause().getMessage());
	    }
	    assertEquals("message upon cancel", exceptionHandler.join());
	}
	
	/**
	 * 9. 取消计算（Java9+）
	 * 
	 * 调用cancel(boolean mayInterruptIfRunning)取消计算。
	 * 对于CompletableFuture类，布尔参数并没有被使用，这是因为它并没有使用中断去取消操作，
	 * 相反，cancel等价于completeExceptionally(new CancellationException())
	 * 
	 */
	@Test
	public void cancelExample() {
		CompletableFuture<String> cf = CompletableFuture.completedFuture("message").thenApplyAsync(String::toUpperCase,
	            CompletableFuture.delayedExecutor(1, TimeUnit.SECONDS));
	    CompletableFuture<String> cf2 = cf.exceptionally(throwable -> "canceled message");
	    assertTrue(cf.cancel(true), "Was not canceled");
	    assertTrue(cf.isCompletedExceptionally(), "Was not completed exceptionally");
	    assertEquals("canceled message", cf2.join());
	}
	
	/**
	 * 10. 在两个完成的阶段其中之一上应用函数
	 * 
	 * 例子创建了CompletableFuture, applyToEither处理两个阶段， 在其中之一上应用函数(保证哪一个被执行)
	 * 本例中的两个阶段一个是应用大写转换在原始的字符串上， 另一个阶段是应用小些转换。
	 */
	@Test
	public void applyToEitherExample() {
		String original = "Message";
	    CompletableFuture<String> cf1 = CompletableFuture.completedFuture(original)
	            .thenApplyAsync(s -> delayedUpperCase(s));
	    CompletableFuture<String> cf2 = cf1.applyToEither(
	            CompletableFuture.completedFuture(original).thenApplyAsync(s -> delayedLowerCase(s)), s -> s + " from applyToEither");
//	    sleepEnough();
	    String result = cf2.join();
	    assertTrue(result.endsWith(" from applyToEither"));
	    System.out.println(result);
	}
	
	/**
	 * 11. 在两个完成的阶段其中之一上调用消费函数（Function变成Consumer）
	 */
	@Test
	public void acceptEitherExample() {
		String original = "Message";
	    StringBuilder result = new StringBuilder();
	    CompletableFuture<Void> cf = CompletableFuture.completedFuture(original)
	            .thenApplyAsync(s -> delayedUpperCase(s))
	            .acceptEither(CompletableFuture.completedFuture(original).thenApplyAsync(s -> delayedLowerCase(s)),
	                    s -> result.append(s).append("acceptEither"));
	    cf.join();
	    assertTrue(result.toString().endsWith("acceptEither"), "结果为空");
	}
	
	/**
	 * 12. 在两个阶段都执行完后运行一个 Runnable
	 * 
	 * 演示了依赖的CompletableFuture如果等待两个阶段完成后执行了一个Runnable。 
	 * 注意下面所有的阶段都是同步执行的，第一个阶段执行大写转换，第二个阶段执行小写转换。
	 */
	@Test
	public void runAfterBothExample() {
		String original = "Message";
		StringBuffer result = new StringBuffer();
	    CompletableFuture.completedFuture(original).thenApply(s -> result.append(s.toUpperCase() + ", ")).runAfterBoth(
	            CompletableFuture.completedFuture(original).thenApply(s -> result.append(s.toLowerCase() + ", ")),
	            () -> result.append("done"));
	    System.out.println(result.toString());
	    assertTrue(result.length() > 0, "结果为空");
	}
	
	/**
	 * 13. 使用BiConsumer处理两个阶段的结果
	 */
	@Test
	public void thenAcceptBothExample() {
		String original = "Message";
	    StringBuilder result = new StringBuilder();
	    CompletableFuture.completedFuture(original).thenApply(String::toUpperCase).thenAcceptBoth(
	            CompletableFuture.completedFuture(original).thenApply(String::toLowerCase),
	            (s1, s2) -> result.append(s1 + s2));
	    assertEquals("MESSAGEmessage", result.toString());
	}
	
	/**
	 * 14. 使用BiFunction处理两个阶段的结果
	 * 
	 * 如果CompletableFuture依赖两个前面阶段的结果， 它复合两个阶段的结果再返回一个结果，我们就可以使用thenCombine()函数。
	 * 整个流水线是同步的，所以getNow()会得到最终的结果，它把大写和小写字符串连接起来。
	 */
	@Test
	public void thenCombineExample() {
		String original = "Message";
	    CompletableFuture<String> cf = CompletableFuture.completedFuture(original).thenApply(s -> delayedUpperCase(s))
	            .thenCombine(CompletableFuture.completedFuture(original).thenApply(s -> delayedLowerCase(s)),
	                    (s1, s2) -> s1 + s2);
	    assertEquals("MESSAGEmessage", cf.getNow(null));
	}
	
	/**
	 * 15. 异步使用BiFunction处理两个阶段的结果
	 * 
	 * 类似上面的例子，不同处是：依赖的前两个阶段异步地执行，所以thenCombine()也异步地执行，即时它没有Async后缀。
	 * 
	 */
	@Test
	public void thenCombineAsyncExample() {
		String original = "Message";
	    CompletableFuture<String> cf = CompletableFuture.completedFuture(original)
	            .thenApplyAsync(s -> delayedUpperCase(s))
	            .thenCombine(CompletableFuture.completedFuture(original).thenApplyAsync(s -> delayedLowerCase(s)),
	                    (s1, s2) -> s1 + s2);
	    assertEquals("MESSAGEmessage", cf.join());
	}
	
	/**
	 * 16. 组合 CompletableFuture
	 * 
	 * 可以使用thenCompose()完成上面两个例子。这个方法等待第一个阶段的完成(大写转换)，
	 * 它的结果传给一个指定的返回CompletableFuture函数，它的结果就是返回的CompletableFuture的结果。
	 * 
	 * 函数需要一个大写字符串做参数，然后返回一个CompletableFuture, 这个CompletableFuture会转换字符串变成小写然后连接在大写字符串的后面
	 */
	@Test
	public void thenComposeExample() {
		String original = "Message";
	    CompletableFuture<String> cf = CompletableFuture.completedFuture(original).thenApply(s -> delayedUpperCase(s))
	            .thenCompose(upper -> CompletableFuture.completedFuture(original).thenApply(s -> delayedLowerCase(s))
	                    .thenApply(s -> upper + s));
	    assertEquals("MESSAGEmessage", cf.join());
	}
	
	/**
	 * 17. 当几个阶段中的一个完成，创建一个完成的阶段
	 * 
	 * 演示：当任意一个CompletableFuture完成后， 创建一个完成的CompletableFuture
	 * 待处理的阶段首先创建， 每个阶段都是转换一个字符串为大写。因为本例中这些阶段都是同步地执行(thenApply), 从anyOf中创建的CompletableFuture会立即完成，
	 * 这样所有的阶段都已完成，我们使用whenComplete(BiConsumer<? super Object, ? super Throwable> action)处理完成的结果。
	 */
	@Test
	public void anyOfExample() {
		StringBuilder result = new StringBuilder();
	    List<String> messages = Arrays.asList("a", "b", "c");
	    List<CompletableFuture<String>> futures = messages.stream()
	            .map(msg -> CompletableFuture.completedFuture(msg).thenApply(s -> delayedUpperCase(s)))
	            .collect(Collectors.toList());
	    CompletableFuture.anyOf(futures.toArray(new CompletableFuture[futures.size()])).whenComplete((res, th) -> {
	        if(th == null) {
	            assertTrue(isUpperCase((String) res));
	            result.append(res);
	        }
	    });
	    System.out.println(result.toString());
	    assertTrue(result.length() > 0, "结果为空");
	}
	
	/**
	 * 18、当所有的阶段都完成后创建一个阶段
	 * 
	 * 演示当所有的阶段完成后才继续处理, 同步的方式
	 */
	@Test
	public void allOfExample() {
		StringBuilder result = new StringBuilder();
	    List<String> messages = Arrays.asList("a", "b", "c");
	    List<CompletableFuture<String>> futures = messages.stream()
	            .map(msg -> CompletableFuture.completedFuture(msg).thenApply(s -> delayedUpperCase(s)))
	            .collect(Collectors.toList());
	    CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()])).whenComplete((v, th) -> {
	        futures.forEach(cf -> assertTrue(isUpperCase(cf.getNow(null))));
	        result.append("done");
	    });
	    System.out.println(result.toString());
	    assertTrue(result.length() > 0, "结果为空");
	}
	
	/**
	 * 19. 当所有的阶段都完成后异步地创建一个阶段
	 * 
	 * 使用thenApplyAsync()替换那些单个的CompletableFutures的方法，allOf()会在通用池中的线程中异步地执行。所以我们需要调用join方法等待它完成。
	 */
	@Test
	public void allOfAsyncExample() {
		StringBuilder result = new StringBuilder();
	    List<String> messages = Arrays.asList("a", "b", "c");
	    List<CompletableFuture<String>> futures = messages.stream()
	            .map(msg -> CompletableFuture.completedFuture(msg).thenApplyAsync(s -> delayedUpperCase(s)))
	            .collect(Collectors.toList());
	    CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()]))
	            .whenComplete((v, th) -> {
	                futures.forEach(cf -> assertTrue(isUpperCase(cf.getNow(null))));
	                result.append("done");
	            });
	    allOf.join();
	    System.out.println(result.toString());
	    assertTrue(result.length() > 0, "结果为空");
	}
	
	/**
	 * 20. 真实的例子
	 * 
	 * 现在你已经了解了CompletionStage 和 CompletableFuture 的一些函数的功能，下面的例子是一个实践场景：
	 * 
	 * 1. 首先异步调用cars方法获得Car的列表，它返回CompletionStage场景。cars消费一个远程的REST API。
	 * 2. 然后我们复合一个CompletionStage填写每个汽车的评分，通过rating(manufacturerId)返回一个CompletionStage, 它会异步地获取汽车的评分(可能又是一个REST API调用)。
	 * 3. 当所有的汽车填好评分后，我们结束这个列表，所以我们调用allOf得到最终的阶段， 它在前面阶段所有阶段完成后才完成。
	 * 4. 在最终的阶段调用whenComplete(),我们打印出每个汽车和它的评分。
	 * 
	 */
	@Test
	public void realExampleWithoutCompletableFuture() {
		long start = System.currentTimeMillis();
		List<CarJUC> cars = cars();
		cars.forEach(car -> {
			float rating = rating(car.manufacturerId);
			car.setRating(rating);
		});
		cars.forEach(System.out::println);
		long end = System.currentTimeMillis();
		System.out.println("Took " + (end - start) + " ms.");
	}
	
	@Test
	public void realExampleWithCompletableFuture() {
		long start = System.currentTimeMillis();
        cars2().thenCompose(cars -> {
            List<CompletionStage<CarJUC>> updatedCars = cars.stream()
                    .map(car -> rating2(car.manufacturerId).thenApply(r -> {
                        car.setRating(r);
                        return car;
                    })).collect(Collectors.toList());

            CompletableFuture<Void> done = CompletableFuture
                    .allOf(updatedCars.toArray(new CompletableFuture[updatedCars.size()]));
            return done.thenApply(v -> updatedCars.stream().map(CompletionStage::toCompletableFuture)
                    .map(CompletableFuture::join).collect(Collectors.toList()));
        }).whenComplete((cars, th) -> {
            if (th == null) {
                cars.forEach(System.out::println);
            } else {
                throw new RuntimeException(th);
            }
        }).toCompletableFuture().join();
        long end = System.currentTimeMillis();
        System.out.println("Took " + (end - start) + " ms.");
	}
	
	static ExecutorService executor = Executors.newFixedThreadPool(4, new ThreadFactory() {
	    int count = 1;
	    @Override
	    public Thread newThread(Runnable runnable) {
	        return new Thread(runnable, "custom-executor-" + count++);
	    }
	});
	
	private static boolean isUpperCase(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (Character.isLowerCase(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private static String delayedUpperCase(String s) {
        randomSleep();
        return s.toUpperCase();
    }

    private static String delayedLowerCase(String s) {
        randomSleep();
        return s.toLowerCase();
    }
    
    private static void randomSleep() {
        try {
        	TimeUnit.MILLISECONDS.sleep((long)(Math.random() * 1000));
        } catch (InterruptedException e) {
            // ...
        }
    }

    private static void sleepEnough() {
        try {
        	TimeUnit.SECONDS.sleep(2L);
        } catch (InterruptedException e) {
            // ...
        }
    }
    
    static float rating(int manufacturer) {
		try {
			simulateDelay();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new RuntimeException(e);
		}
		switch (manufacturer) {
		case 2:
			return 4f;
		case 3:
			return 4.1f;
		case 7:
			return 4.2f;
		default:
			return 5f;
		}
	}

	static List<CarJUC> cars() {
		List<CarJUC> carList = new ArrayList<>();
		carList.add(new CarJUC(1, 3, "Fiesta", 2017));
		carList.add(new CarJUC(2, 7, "Camry", 2014));
		carList.add(new CarJUC(3, 2, "M2", 2008));
		return carList;
	}

	private static void simulateDelay() throws InterruptedException {
		Thread.sleep(5000);
	}
	
	static CompletionStage<Float> rating2(int manufacturer) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                simulateDelay();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
            switch (manufacturer) {
            case 2:
                return 4f;
            case 3:
                return 4.1f;
            case 7:
                return 4.2f;
            default:
                return 5f;
            }
        }).exceptionally(th -> -1f);
    }

    static CompletionStage<List<CarJUC>> cars2() {
        List<CarJUC> carList = new ArrayList<>();
        carList.add(new CarJUC(1, 3, "Fiesta", 2017));
        carList.add(new CarJUC(2, 7, "Camry", 2014));
        carList.add(new CarJUC(3, 2, "M2", 2008));
        return CompletableFuture.supplyAsync(() -> carList);
    }
}
