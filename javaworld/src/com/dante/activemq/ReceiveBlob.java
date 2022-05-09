package com.dante.activemq;

import java.io.InputStream;
import java.util.List;

import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQSession;
import org.apache.activemq.BlobMessage;
import org.apache.activemq.command.ActiveMQBlobMessage;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.commons.io.IOUtils;

public class ReceiveBlob {

	public static void main(String[] args) {
		try {
			ActiveMQConnectionFactory factoryA = new ActiveMQConnectionFactory("tcp://localhost:61616?jms.blobTransferPolicy.defaultUploadUrl=http://localhost:8161/fileserver/");  
			  
            Queue queue = new ActiveMQQueue("deerjet.adsb");  
            ActiveMQConnection conn = (ActiveMQConnection) factoryA.createConnection();  
              
            conn.start();  
            ActiveMQSession session = (ActiveMQSession) conn.createSession(false, Session.AUTO_ACKNOWLEDGE); 
            
            MessageConsumer consumer = session.createConsumer(queue);  
            
            MessageListener listener = new MessageListener() {  
                public void onMessage(Message message) {  
                    try {  
                        if (message instanceof BlobMessage) {  
                              
                            System.out.println("filename:"+message.getStringProperty("FILE.NAME"));  
                            System.out.println("filesize:"+message.getLongProperty("FILE.SIZE"));  
                              
                              BlobMessage blobMessage = (BlobMessage) message;  
                              InputStream in = blobMessage.getInputStream();  
                              List<String> list = IOUtils.readLines(in);  
                              for(String s : list) {
                            	  System.out.println(s);  
                              }
                              in.close();
                              ((ActiveMQBlobMessage)blobMessage).deleteFile();//注意处理完后需要手工删除服务器端文件    
                        }  
                    } catch (Exception e) {  
                        e.printStackTrace();  
                    }  
                }  
            };  
            consumer.setMessageListener(listener); 
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
