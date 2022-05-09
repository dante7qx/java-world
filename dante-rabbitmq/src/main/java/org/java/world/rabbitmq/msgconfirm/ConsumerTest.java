package org.java.world.rabbitmq.msgconfirm;

import java.io.IOException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class ConsumerTest implements Runnable {
	
	private final static String QUEUE_NAME = "confirm_test";
	private final static int MSG_COUNT = 15;
	
	@Override
	public void run() {
		try {
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost("localhost");
			Connection connenction = factory.newConnection();
			Channel channel = connenction.createChannel();
			channel.queueDeclare(QUEUE_NAME, true, false, false, null);
			
			for (int i = 0; i < MSG_COUNT; i++) {
				// 创建消息消费者
				Consumer consumer = new DefaultConsumer(channel) {
					@Override
					public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
							byte[] body) throws IOException {
						String msg = new String(body, "UTF-8");
						System.out.println(" [x] Received '" + msg + "'");
						// 通知已经处理完消息
						channel.basicAck(envelope.getDeliveryTag(), false);
					}
				};
				// 自动确认
				channel.basicConsume(QUEUE_NAME, false, consumer);
            }
			channel.close();
			connenction.close();
		} catch (Exception e) {
			System.out.println("damn fuck! some error happened!");
            e.printStackTrace();
		}
		
	}

}
