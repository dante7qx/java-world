package org.java.world.dante.reactor;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.java.world.dante.reactor.event.MsgEvent;
import org.java.world.dante.reactor.event.source.MsgEventSource;
import org.java.world.dante.reactor.listener.MsgEventListener;
import org.junit.Test;

import reactor.core.publisher.Flux;

/**
 * 自定义数据流
 * 
 * @author dante
 *
 */
public class DataStreamTest {

	/**
	 * public static <T> Flux<T> generate(Consumer<SynchronousSink<T>> generator)
	 */
	@Test
	public void testGenerate1() {
		final AtomicInteger count = new AtomicInteger(1); 
		Flux<Object> generatorPublisher = Flux.generate(sink -> {
			sink.next(count + " -> " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
			try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
			if(count.getAndIncrement() >= 5) {
				sink.complete();
			}
		});
		generatorPublisher.subscribe(System.out::println);
	}
	
	/**
	 * public static <T, S> Flux<T> generate(Callable<S> stateSupplier, BiFunction<S, SynchronousSink<T>, S> generator)
	 */
	@Test
	public void testGenerate2() {
		Flux<Object> generatorPublisher = Flux.generate(
				() -> 1,
				(count, sink) -> {
					sink.next(count + " -> " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
					try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (count >= 5) {
                        sink.complete();
                    }
                    if(count == 4) {
						sink.error(new RuntimeException("数字超出了指定的完成范围。"));
					}
                    
					return count + 1;
		});
		generatorPublisher.subscribe(System.out::println, e -> e.printStackTrace());
	}
	
	/**
	 * public static <T> Flux<T> create(Consumer<? super FluxSink<T>> emitter)
	 * @throws InterruptedException 
	 */
	@Test
	public void testCreate1() throws InterruptedException {
		MsgEventSource eventSource = new MsgEventSource();
		Flux.create(sink -> {
			eventSource.register(new MsgEventListener() {
				@Override
				public void onNewEvent(MsgEvent evt) {
					sink.next(evt);	// 监听器在收到事件回调的时候通过sink将事件再发出
				}
				
				@Override
				public void onEventStopped() {
					sink.complete();	// 监听器再收到事件源停止的回调的时候，通过sink发出完成信号
				}
				
			});
		}).subscribe(System.out::println);
		
		for (int i = 1; i <= 20; i++) {
			// 循环产生20个事件，每个间隔不超过1秒的随机时间
			Random random = new Random();
			TimeUnit.MILLISECONDS.sleep(random.nextInt(1000));
			eventSource.newEvent(new MsgEvent("事件 - " + i, Date.from(Instant.now())));
		}
		
		// 停止监听事件
		eventSource.eventStopped();
	}
}
