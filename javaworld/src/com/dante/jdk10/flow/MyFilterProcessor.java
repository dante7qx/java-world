package com.dante.jdk10.flow;

import java.util.Arrays;
import java.util.concurrent.Flow;
import java.util.concurrent.Flow.Subscription;
import java.util.concurrent.SubmissionPublisher;
import java.util.function.Function;

public class MyFilterProcessor<T, K extends T> extends SubmissionPublisher<K> implements Flow.Processor<T, K> {

	private Function<? super T, Boolean> function;
	private Flow.Subscription subscription;

	public MyFilterProcessor(Function<? super T, Boolean> function) {
		super();
		this.function = function;
	}

	@Override
	public void onSubscribe(Subscription subscription) {
		this.subscription = subscription;
		subscription.request(1);
	}

	@Override
	public void onNext(T item) {
		if (!(boolean) function.apply(item)) {
			submit((K) item);
		}
		subscription.request(1);
	}

	@Override
	public void onError(Throwable t) {
		t.printStackTrace();
	}

	@Override
	public void onComplete() {
		close();
	}

	public static void main(String[] args) throws InterruptedException {
		//Create Publisher
        SubmissionPublisher<String> publisher = new SubmissionPublisher<>();
        //Create Processor and Subscriber
        MyFilterProcessor<String, String> filterProcessor = new MyFilterProcessor<>(s -> s.equals("x"));
        MySubscriber<String> subscriber = new MySubscriber<>();
        //Chain Processor and Subscriber
        publisher.subscribe(filterProcessor);
        filterProcessor.subscribe(subscriber);
        System.out.println("Publishing Items...");
        String[] items = {"1", "x", "2", "x", "3", "x"};
        Arrays.asList(items).stream().forEach(i -> publisher.submit(i));
        publisher.close();
        Thread.sleep(2000);
	}
}
