package com.dante.mail;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;


public class MailBoxDemo {
	private MimeMessage mimeMessage = null;
	private StringBuffer bodyText = new StringBuffer(); // 存放邮件内容的StringBuffer对象
	private String dateFormat = "yyyy-MM-dd hh:mm:ss"; // 默认的日前显示格式
	
	public MailBoxDemo() {
	}
	
	public MailBoxDemo(MimeMessage mimeMessage) {
		this.mimeMessage = mimeMessage;
	}
	
	public void setMimeMessage(MimeMessage mimeMessage) {  
        this.mimeMessage = mimeMessage;  
    } 
	
	/**
	 * 获得发件人的地址和姓名 　
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getFrom() throws Exception {  
        InternetAddress address[] = (InternetAddress[]) mimeMessage.getFrom();  
        String from = address[0].getAddress();  
        if (from == null) {  
            from = "";  
            System.out.println("无法知道发送者.");  
        }  
        String personal = address[0].getPersonal();  
  
        if (personal == null) {  
            personal = "";  
            System.out.println("无法知道发送者的姓名.");  
        }  
  
        String fromAddr = null;  
        if (personal != null || from != null) {  
            fromAddr = personal + "<" + from + ">";  
            System.out.println("发送者是：" + fromAddr);  
        } else {  
            System.out.println("无法获得发送者信息.");  
        }  
        return fromAddr;  
    }  
	
	/**  
     * 　*　获得邮件主题 　  
     */  
    public String getSubject() throws MessagingException {  
        String subject = "";  
        try {  
            subject = MimeUtility.decodeText(mimeMessage.getSubject());  
            if (subject == null) {  
                subject = "";  
            }  
        } catch (Exception exce) {  
            exce.printStackTrace();  
        }  
        return subject;  
    }
    
    /**  
     * 获得邮件发送日期 　  
     */  
    public String getSentDate() throws Exception {  
        Date sentDate = mimeMessage.getSentDate();  
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);  
        String strSentDate = format.format(sentDate);  
        return strSentDate;  
    }
    
    /**  
     * 　　*　解析邮件，把得到的邮件内容保存到一个StringBuffer对象中，解析邮件  
     * 　　*　主要是根据MimeType类型的不同执行不同的操作，一步一步的解析 　　  
     */  
  
    public void getMailContent(Part part) throws Exception {  
        String contentType = part.getContentType();  
        System.out.println("邮件的MimeType类型: " + contentType);  
        int nameIndex = contentType.indexOf("name");  
        boolean conName = false;  
        if (nameIndex != -1) {  
            conName = true;  
        }  
        System.out.println("邮件内容的类型:　" + contentType);  
        if (part.isMimeType("text/plain") && conName == false) {  
            // text/plain 类型  
            bodyText.append((String) part.getContent());  
        } else if (part.isMimeType("text/html") && conName == false) {  
            // text/html 类型  
            bodyText.append((String) part.getContent());  
        } else if (part.isMimeType("multipart/*")) {  
            Multipart multipart = (Multipart) part.getContent();  
            int counts = multipart.getCount();  
            for (int i = 0; i < counts; i++) {  
                getMailContent(multipart.getBodyPart(i));  
            }  
        } else if (part.isMimeType("message/rfc822")) {  
            getMailContent((Part) part.getContent());  
        } else {  
        }  
    } 
    
    /**  
     * 获得邮件正文内容 　  
     */  
    public String getBodyText() {
        return bodyText.toString();  
    }
    
    /**  
     * 判断此邮件是否已读，如果未读返回false,反之返回true  
     */  
    public boolean isNew() throws MessagingException {  
        boolean isNew = false;  
        Flags flags = ((Message) mimeMessage).getFlags();  
        Flags.Flag[] flag = flags.getSystemFlags();  
        for (int i = 0; i < flag.length; i++) {  
            if (flag[i] == Flags.Flag.SEEN) {  
                isNew = true;  
                // break;  
            }  
        }  
        return isNew;  
    }  
    
    /**  
     *　获得此邮件的Message-ID 　　  
     */  
    public String getMessageId() throws MessagingException {  
        return mimeMessage.getMessageID();
    }
    
	
	public static void main(String[] args) throws Exception {
		String host = "pop.163.com";  
        String username = "sunchao.0129";  
        String password = "Dante2012";
		
        Properties props = new Properties();  
        props.setProperty("mail.store.protocol", "pop3");
        Session session = Session.getDefaultInstance(props, null);  
  
        Store store = session.getStore("pop3");  
        store.connect(host, username, password);
        System.out.println(store);
        Folder folder = store.getFolder("INBOX");  
        folder.open(Folder.READ_ONLY);  
        Message message[] = folder.getMessages();  
        System.out.println("邮件数量:　" + message.length);  
        MailBoxDemo re = null; 
        
        int total = message.length - 1;
        
        for (int i = total; i > total - 2; i--) {  
        	 re = new MailBoxDemo((MimeMessage) message[i]); 
        	 System.out.println("邮件　[" + i + "] - " + re.getMessageId());
        	 System.out.println("主题:　" + re.getSubject()); 
        	 System.out.println("发件人:　" + re.getFrom()); 
        	 System.out.println("发送时间:　" + re.getSentDate());
        	 re.getMailContent((Part) message[i]);
        	 System.out.println("正文内容:　\r\n" + re.getBodyText());
        	 System.out.println("****************************************************************");
        	   
        }
	}
}
