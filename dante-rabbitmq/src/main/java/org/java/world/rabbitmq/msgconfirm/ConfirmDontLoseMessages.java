package org.java.world.rabbitmq.msgconfirm;

import java.io.IOException;

public class ConfirmDontLoseMessages {

	public static void main(String[] args) throws IOException, InterruptedException {
		Thread consumerThread = new Thread(new ConsumerTest());
		Thread publisherThread = new Thread(new PublisherTest());
		// 开启消费者线程
		consumerThread.start();
		// 开启生产者线程
		publisherThread.start();
	}
}
