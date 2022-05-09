package com.dante.jdk10.flow;

import java.util.Arrays;
import java.util.concurrent.Flow;
import java.util.concurrent.Flow.Subscription;
import java.util.concurrent.SubmissionPublisher;

public class MySubscriber<T> implements Flow.Subscriber<T> {

	private Flow.Subscription subscription;

	@Override
	public void onSubscribe(Subscription subscription) {
		this.subscription = subscription;
		// 反向控制获取数据个数。这里要使用Long.MAX_VALUE就会被认为获取无穷的数据。
		subscription.request(1); 
	}

	@Override
	public void onNext(T item) {
		System.out.println("Got : " + item);
		subscription.request(1); // 这里也可以使用Long.MAX_VALUE
	}

	@Override
	public void onError(Throwable t) {
		t.printStackTrace();
	}

	@Override
	public void onComplete() {
		System.out.println("Done");
	}

	public static void main(String[] args) throws InterruptedException {
		SubmissionPublisher<String> publisher = new SubmissionPublisher<>();
		// Register Subscriber
		MySubscriber<String> subscriber = new MySubscriber<>();
		publisher.subscribe(subscriber);
		// Publish items
		System.out.println("Publishing Items...");
		String[] items = { "1", "x", "2", "x", "3", "x" };
		// publisher.submit(i)。 发布数据，相当于数据输入
		Arrays.asList(items).stream().forEach(i -> publisher.submit(i));
		publisher.close();
		Thread.sleep(20000);
	}
}
