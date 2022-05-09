package com.dante.activemq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;


public class Publisher {

	private static String brokerURL = "tcp://10.70.35.28:9606";
	private static String userName = "DEERJET_Activemq";
	private static String password = "Deerjet_20150601!";
	
//	private static String brokerURL = "tcp://localhost:61616";
//	private static String userName = "dante";
//	private static String password = "superdante2012";
	private static transient ConnectionFactory factory;
	private transient Connection connection;
	private transient Session session;
	private transient MessageProducer producer;

	public Publisher() throws JMSException {
		// 连接工厂，JMS 用它创建连接  
		factory = new ActiveMQConnectionFactory(userName, password, brokerURL);
		// JMS 客户端到JMSProvider 的连接  
		connection = factory.createConnection();
		try {
			connection.start();
		} catch (JMSException jmse) {
			connection.close();
			// TODO:业务逻辑处理
			throw jmse;
		}
		//Session：发送或接收消息的线程  
        // 获取session
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
	}
	
	public void close() throws JMSException {  
		if(session != null) {
			session.close();
		}
        if (connection != null) {  
            connection.close();  
        }  
    } 
	
	public void sendByTopic() {
		try {
			// 消息的目的地，消息发送到的主题
			Destination destination = session.createTopic("ADSB.Aero");
			// MessageProducer：消息发送者（生产者）  
			// 创建消息发送者
		    producer = session.createProducer(destination);
			// 设置是否持久化
			// DeliveryMode.NON_PERSISTENT：不持久化
			// DeliveryMode.PERSISTENT：持久化
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
			
			StringBuffer buffer = new StringBuffer();
			buffer.append("78031B,OKAir,ZH9628,B-6316,250.404938,119.248596,41.277145,-19,607,9784,TSNBK1,TSNBK")
			.append("\n")
			.append("780B21,SH,ZH9623,,297.995514,109.522804,21.598022,0,729,10393,ZGBH,ZGBH")
			.append("\n")
			.append("780317,HNAir,ZH9996,B-5322,102.916779,111.042786,23.321089,0,919,10088,HAKHU1,HAKHU1");
			
			TextMessage message =  session.createTextMessage();
			message.setText(buffer.toString());
			producer.send(message);
			System.out.println("Send Message: \n" + message.getText());
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendByQueue() {
		try {
			// 消息的目的地，消息发送到的主题
//			Destination destination = session.createQueue("deerjet.queue.adsb");
			Destination destination = session.createQueue("deerjet.activemq.queue.adsb");
			// MessageProducer：消息发送者（生产者）  
			// 创建消息发送者
		    producer = session.createProducer(destination);
			// 设置是否持久化
			// DeliveryMode.NON_PERSISTENT：不持久化
			// DeliveryMode.PERSISTENT：持久化
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
			
			StringBuffer buffer = new StringBuffer();
			buffer.append("780bef,SYAirport,ZH9601,,0,123.486252,41.637096,0,0,0,ZYTX,ZYTX")
			.append("\n")
			.append("78031B,OKAir,ZH9628,B-6316,250.404938,119.248596,41.277145,-19,607,9784,TSNBK1,TSNBK1")
			.append("\n")
			.append("780B21,SH,ZH9623,,297.995514,109.521072,21.598843,0,729,10393,ZGBH,ZGBH");
			
			TextMessage message =  session.createTextMessage();
			message.setText(buffer.toString());
//			message.setText("我不知道！");
			producer.send(message);
			System.out.println("Send Message: \n" + message.getText());
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws JMSException {
		Publisher publisher = new Publisher();
		publisher.sendByTopic();
//		publisher.sendByQueue();
		publisher.close();
	}
	
}
