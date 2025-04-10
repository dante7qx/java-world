package org.java.world.rabbitmq.rpc;

import java.io.IOException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class RPCServer {
	private static final String RPC_QUEUE_NAME = "rpc_queue";

	private static int fib(int n) {
		if (n == 0) {
			return 0;
		}
		if (n == 1) {
			return 1;
		}
		return fib(n - 1) + fib(n - 2);
	}

	public static void main(String[] args) {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");

		Connection connection = null;
		try {
			connection = factory.newConnection();
			Channel channel = connection.createChannel();

			channel.queueDeclare(RPC_QUEUE_NAME, false, false, false, null);

			channel.basicQos(1);

			System.out.println(" [x] Awaiting RPC requests");

			Consumer consumer = new DefaultConsumer(channel) {
				@Override
				public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
						byte[] body) throws IOException {
					AMQP.BasicProperties replyProps = new AMQP.BasicProperties.Builder()
							.correlationId(properties.getCorrelationId()).build();

					String response = "";

					try {
						String message = new String(body, "UTF-8");
						int n = Integer.parseInt(message);

						System.out.println(" [.] fib(" + message + ")");
						response += fib(n);
					} catch (RuntimeException e) {
						System.out.println(" [.] " + e.toString());
					} finally {
						channel.basicPublish("", properties.getReplyTo(), replyProps, response.getBytes("UTF-8"));
						channel.basicAck(envelope.getDeliveryTag(), false);
					}
				}
			};

			channel.basicConsume(RPC_QUEUE_NAME, false, consumer);

			// loop to prevent reaching finally block
			while (true) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException _ignore) {
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (java.util.concurrent.TimeoutException e1) {
			e1.printStackTrace();
		} finally {
			if (connection != null)
				try {
					connection.close();
				} catch (IOException _ignore) {
				}
		}
	}
}
