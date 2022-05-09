package org.java.world.rabbitmq.msgconfirm;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class PublisherTest implements Runnable {
	
	private final static String QUEUE_NAME = "confirm_test";
	private final static int MSG_COUNT = 15;

	@Override
	public void run() {
		try {
			long startTime = System.currentTimeMillis();
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost("localhost");
			Connection connenction = factory.newConnection();
			Channel channel = connenction.createChannel();
			channel.queueDeclare(QUEUE_NAME, true, false, false, null);
			// 开启通道上的 publisher acknowledgements (确认通知)
			channel.confirmSelect();
			// 发送持久化消息
			for (int i = 0; i < MSG_COUNT; i++) {
				channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_BASIC, ("SEND_MSG["+i+"]").getBytes());
			}
			
			// 等待所有消息都被ack或者nack，如果某个消息被nack，则抛出IOException
			channel.waitForConfirmsOrDie();
			
			long endTime = System.currentTimeMillis();
			System.out.printf("Publisher->测试花费时间：%.3fs\n", (float) (endTime - startTime) / 1000);
			
			// 删除队列，不论是否在使用中
			channel.queueDelete(QUEUE_NAME);
			
			channel.close();
			connenction.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
