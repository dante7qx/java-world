package org.java.world.dante.reactor.subscriber;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.java.world.dante.reactor.event.MsgEvent;
import org.reactivestreams.Subscription;

import reactor.core.publisher.BaseSubscriber;

/**
 * 慢的消费者
 * 
 * @author dante
 *
 */
public class SlowSubscriber extends BaseSubscriber<MsgEvent> {
	
	private CountDownLatch countDownLatch;
	private long processTime;	// 处理数据的时间
	
	public SlowSubscriber(CountDownLatch countDownLatch, long processTime) {
		this.countDownLatch = countDownLatch;
		this.processTime = processTime;
	}

	@Override
	protected void hookOnSubscribe(Subscription subscription) {
		request(1);	// 订阅时请求1个数据
	}
	
	@Override
	protected void hookOnNext(MsgEvent evt) {
		System.out.println("Subscriber 消费数据 <== " + evt);
		
		 // 订阅者每30毫秒处理1个数据
		 try {
             TimeUnit.MILLISECONDS.sleep(processTime);
         } catch (InterruptedException e) {
         }
		
		request(1); // 处理完1个数据，再请求1个数据
	}
	
	@Override
	protected void hookOnError(Throwable throwable) {
		System.err.println("Subscriber 消费错误 <<< " + throwable);
	}
	
	@Override
	protected void hookOnComplete() {
		countDownLatch.countDown();
	}
	
}
