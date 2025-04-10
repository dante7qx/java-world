package org.java.world.rabbitmq.direct;

import java.io.IOException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class ReceiveLogsDirect {
	private static final String EXCHANGE_NAME = "direct_logs";

	public static void main(String[] argv) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		factory.setUsername("dante");
		factory.setPassword("123456");
		factory.setVirtualHost("springboot");
		
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT.getType());
		String queueName = channel.queueDeclare().getQueue();

		argv = new String[] {"warning", "error"};
		
		if (argv.length < 1) {
			System.err.println("Usage: ReceiveLogsDirect [info] [warning] [error]");
			System.exit(1);
		}

		for (String severity : argv) {
			channel.queueBind(queueName, EXCHANGE_NAME, severity);
		}
		System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				String message = new String(body, "UTF-8");
				System.out.println(" 收到消息 '" + envelope.getRoutingKey() + "':'" + message + "'");
			}
		};
		channel.basicConsume(queueName, true, consumer);
	}
}
