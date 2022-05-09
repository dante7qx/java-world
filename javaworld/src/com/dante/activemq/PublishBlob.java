package com.dante.activemq;

import java.io.File;

import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQSession;
import org.apache.activemq.BlobMessage;
import org.apache.activemq.command.ActiveMQQueue;

public class PublishBlob {

	public static void main(String[] args) {  
        try {  
  
            ActiveMQConnectionFactory factoryA = new ActiveMQConnectionFactory("tcp://localhost:61616?jms.blobTransferPolicy.defaultUploadUrl=http://localhost:8161/fileserver/");  
  
            Queue queue = new ActiveMQQueue("deerjet.adsb");  
            ActiveMQConnection conn = (ActiveMQConnection) factoryA.createConnection();  
            
            conn.start();  
            ActiveMQSession session = (ActiveMQSession) conn.createSession(false, Session.AUTO_ACKNOWLEDGE);  
            
            File file = new File("/Users/dante/Documents/Project/javaworld/src/com/dante/activemq/20141224000000.dat");  
            MessageProducer producer = session.createProducer(queue);  
            BlobMessage blobMessage = session.createBlobMessage(file);  
            blobMessage.setStringProperty("FILE.NAME",file.getName());   
            blobMessage.setLongProperty("FILE.SIZE",file.length());   
            producer.send(blobMessage);  
  
        } catch (Exception e) {  
            e.printStackTrace();
        }  
	}
	
}
