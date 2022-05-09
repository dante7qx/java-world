package com.dante.mail;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class JavaMailDemo {
	private final static String host = "smtp.qq.com";
	private static Session session;
	
	public JavaMailDemo() {
		Properties props = new Properties();
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.auth", "true");
		session = Session.getInstance(props);
	}

	private void sendEmail() throws Exception {
		//发件地址 
		MimeMessage message = new MimeMessage(session);                     
		Address address = new InternetAddress("123456789@qq.com");          
		message.setFrom(address);          
		//收件地址           
		Address toAddress = new InternetAddress("123456789@qq.com");          
		message.setRecipient(MimeMessage.RecipientType.TO, toAddress);                     
		//主题           
		message.setSubject("Hello world");         
		//正文          
		message.setText("Hello world");                        
		message.saveChanges();            
		//Exception in thread "main" javax.mail.NoSuchProviderException: smtp         
		session.setDebug(true);           
		Transport transport = session.getTransport("smtp");         
		transport.connect("smtp.qq.com", "123456789@qq.com", "****");          
		//发送           
		transport.sendMessage(message, message.getAllRecipients());          
		transport.close(); 
	}

	public static void main(String[] args) throws Exception {
		JavaMailDemo demo = new JavaMailDemo();
		demo.sendEmail();
		
	}
}
