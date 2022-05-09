package com.dante.activemq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * 
 * Number Of Pending Messages  是队列中有多少个消息等待出队列
 * Number Of Consumers  是队列中有多少个消费者
 * Messages Enqueued  是队列共有多少个信息
 * Messages Dequeued  是队列中已经出列多少个消息
 * 
 * @author dante
 *
 */
public class Receiver {
	private static String brokerURL = "tcp://localhost:61616";
	private static String userName = "dante";
	private static String password = "superdante2012";
	private static ConnectionFactory factory;
	private Connection connection;
	private Session session;
	
	public Receiver() throws JMSException {
		factory = new ActiveMQConnectionFactory(userName, password, brokerURL);  
        connection = factory.createConnection();  
        connection.start();  
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);  
	}
	
	public Session getSession() {  
        return session;  
    } 
	
	public void close() throws JMSException {  
		
        if (connection != null) {  
            connection.close();  
        }  
    }
	
	public static void main(String[] args) {
		try {
			Receiver receiver = new Receiver();
//			receiver.reveiveByTopic(receiver);
			receiver.reveiveByQueue(receiver);
		} catch(JMSException e) {
			e.printStackTrace();
		}
		
	}
	
	private void reveiveByTopic(Receiver receiver) throws JMSException {
		Destination destination = receiver.getSession().createTopic("deerjet.topic.adsb");
		MessageConsumer consumer = receiver.getSession().createConsumer(destination);
		consumer.setMessageListener(new MessageListener() {
			@Override
			public void onMessage(Message message) {
				TextMessage textMessage = (TextMessage) message;
				if(textMessage != null) {
					try {
						System.out.println("Receive Message: \n" + textMessage.getText());
					} catch (JMSException e) {
						e.printStackTrace();
					}
				} else {
					System.out.println("No Message received.");
				}
			}
		});
	}
	
	private void reveiveByQueue(Receiver receiver) throws JMSException {
		Destination destination = receiver.getSession().createQueue("deerjet.queue.adsb");
		MessageConsumer consumer = receiver.getSession().createConsumer(destination);
		consumer.setMessageListener(new MessageListener() {
			@Override
			public void onMessage(Message message) {
				TextMessage textMessage = (TextMessage) message;
				if(textMessage != null) {
					try {
						System.out.println("Receive Message: \n" + textMessage.getText());
					} catch (JMSException e) {
						e.printStackTrace();
					}
				} else {
					System.out.println("No Message received.");
				}
			}
		});
	}
	
}
