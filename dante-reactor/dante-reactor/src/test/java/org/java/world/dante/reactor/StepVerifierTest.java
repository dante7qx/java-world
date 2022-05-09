package org.java.world.dante.reactor;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.reactivestreams.Subscription;

import reactor.core.Disposable;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class StepVerifierTest {
	
	@Test
	public void testMono() {
		Mono<String> monoStr = Mono.just("Hello World");
		Disposable disp = monoStr.log().subscribe(
			s -> System.err.println(s),
			e -> e.printStackTrace(),
			() -> System.out.println("数据流已经消费完成。")
		);
		System.out.println(disp.isDisposed());
			
	}
	
	@Test
	public void testOp() {
		StepVerifier.create(Flux.range(1, 6)
			.log()
			.map(x -> x * x))
			.expectNext(1, 4, 9, 16, 25, 36)
			.verifyComplete();
	}
	
	@Test
	public void testStreamCompose() {
		StepVerifier.create(
			Flux.just("Flux", "Mono")
			.flatMap(s -> Flux.fromArray(s.split("\\s*")))		// 对于每一个字符串s，将其拆分为包含一个字符的字符串流；
			.delayElements(Duration.ofMillis(1000))				// 对每个元素延迟100ms；
			.doOnNext(System.out::println))						// 对每个元素进行打印（注doOnNext方法是“偷窥式”的方法，不会消费数据流）；
		.expectNextCount(8)										// 验证是否发出了8个元素
		.verifyComplete();
	}
	
	@Test
	public void testFilter() {
		StepVerifier.create(Flux.range(1,  6)
				.filter(i -> i % 2 == 1)
				.map(i -> i * i))
			.expectNext(1, 9, 25)
			.verifyComplete();
			
	}
	
	@Test
	public void testZip() throws InterruptedException {
		// 等待执行1次countDown方法后结束
		CountDownLatch countDownLatch = new CountDownLatch(1);
	    Flux.zip(
	            getZipDescFlux(),
	            Flux.interval(Duration.ofMillis(200)))  // 每200ms发出一个元素的long数据流
	    		// zip之后的流中元素类型为Tuple2
	            .subscribe(t -> System.out.println(t.getT1()), null, countDownLatch::countDown);
	    countDownLatch.await(10, TimeUnit.SECONDS);     // 等待countDown倒数至0，最多等待10秒钟
	}
	
	
	private Flux<String> getZipDescFlux() {
	    String desc = "Zip two sources together, that is to say wait for all the sources to emit one element and combine these elements once into a Tuple2.";
	    return Flux.fromArray(desc.split("\\s+"));
	}
	
	/**
	 * retry 重新对数据源进行订阅，重试是一个新的数据源，之前的数据序列已经在遇到 error signal 时终止了
	 * 
	 * @throws InterruptedException
	 */
	@Test
	public void testRetry() throws InterruptedException {
		Flux.range(1, 6)
			.map(i -> 10 / (3 - i))
			.retry(1)
			.subscribe(System.out::println, System.err::println);
		Thread.sleep(200);
	}
	
	/**
	 * 回压 - 流量控制
	 * 
	 * 1. 扩展 BaseSubscriber，自定义具有流量控制的 Subscriber
	 * 2. subscribe(System.out::println) 表示接收无限的请求(unbounded request)
	 */
	@Test
	public void testBackPressure() {
		Flux.range(1, 6)
			.doOnRequest(n -> System.out.println("请求的个数 " + n))
			.subscribe(new BaseSubscriber<Integer>() {
				// 在订阅的时候执行的操作
				@Override
				protected void hookOnSubscribe(Subscription subscription) {
					System.out.println("Subscribed and make a request...");
					// 订阅时首先向上游请求1个元素
					request(1);
				}
				
				// 每次在收到一个元素的时候的操作
				@Override
				protected void hookOnNext(Integer value) {
					try {
						// sleep 1秒钟来模拟慢的Subscriber
                        TimeUnit.SECONDS.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
					// 每次收到的元素
                    System.out.println("Get value [" + value + "]"); 
                    // 每次处理完1个元素后再请求1个
                    request(1); // 9
				}
			});
	}
	
}
