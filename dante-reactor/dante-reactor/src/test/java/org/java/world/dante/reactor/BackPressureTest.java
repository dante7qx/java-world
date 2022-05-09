package org.java.world.dante.reactor;

import java.time.Instant;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.java.world.dante.reactor.event.MsgEvent;
import org.java.world.dante.reactor.event.source.MsgEventSource;
import org.java.world.dante.reactor.listener.MsgEventListener;
import org.java.world.dante.reactor.subscriber.SlowSubscriber;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.scheduler.Schedulers;

public class BackPressureTest {
	
	private static final int EVENT_COUNT = 20;		// 10个事件
	private static final long EVENT_DURATION = 20L; // 20毫秒生产一个事件
	private static final long CONSUMER_TIME = 40L;
	
	private MsgEventSource eventSource;	// 事件源
	private CountDownLatch countDownLatch;
	private Flux<MsgEvent> fastPublisher;	
	private SlowSubscriber slowSubscriber;
	
	@Before
	public void init() {
		eventSource = new MsgEventSource();
		countDownLatch = new CountDownLatch(1);
		slowSubscriber = new SlowSubscriber(countDownLatch, CONSUMER_TIME);
	}
	
	@After
    public void subscribe() throws InterruptedException {
        fastPublisher.subscribe(slowSubscriber);
        generateEvent(EVENT_COUNT, EVENT_DURATION);
        // 使用CountDownLatch等待订阅者处理完成
        countDownLatch.await(1, TimeUnit.MINUTES);
    } 
	
	/**
	 * 测试回压策略
	 */
	@Test
	public void testBackpressureStrategy() {
		// 后续的操作符和订阅者运行在一个单独的名为 "【新线程】" 的线程上。（发布者和订阅者并不在同一个线程上）
		// .publishOn作为订阅者每次向上游request的个数，默认为256，所以一定程度上也起到了缓存的效果
		fastPublisher
//			= createFastPublisher(FluxSink.OverflowStrategy.BUFFER)	// 等同于 .onBackpressureBuffer()
//			= createFastPublisher(FluxSink.OverflowStrategy.DROP)	// 等同于 .onBackpressureDrop()
			= createFastPublisher(FluxSink.OverflowStrategy.LATEST)	// 等同于 .onBackpressureLatest()
//			= createFastPublisher(FluxSink.OverflowStrategy.ERROR)	// 等同于 .onBackpressureError()
//			= createFastPublisher(FluxSink.OverflowStrategy.IGNORE)	
			.doOnRequest(n -> System.out.println("		=== 请求数据数目 " + n + " ==="))
			.publishOn(Schedulers.newSingle("【新线程】"), 1);
	}
	
	
	
	/**
	 * 创造快速率的 Publisher
	 * 
	 * @param backpressureStrategy
	 * @return
	 */
	private Flux<MsgEvent> createFastPublisher(FluxSink.OverflowStrategy backpressureStrategy) {
		return Flux.create(sink -> eventSource.register(new MsgEventListener() {
			@Override
			public void onNewEvent(MsgEvent evt) {
				System.out.println("Publisher 发出数据 ==> " + evt);
				sink.next(evt);
			}
			@Override
			public void onEventStopped() {
				sink.complete();
			}
		}), backpressureStrategy);
	}
	
	/**
	 * 产生新的 Event
	 * 
	 * @param times  Event的个数
	 * @param millis 每个Event的时间间隔
	 */
	private void generateEvent(int times, long millis) {
		for (int i = 1; i <= times; i++) {
            try {
                TimeUnit.MILLISECONDS.sleep(millis);
            } catch (InterruptedException e) {
            	e.printStackTrace();
            }
            eventSource.newEvent(new MsgEvent("事件(" + i + ")", Date.from(Instant.now())));
        }
        eventSource.eventStopped();
	}

}
