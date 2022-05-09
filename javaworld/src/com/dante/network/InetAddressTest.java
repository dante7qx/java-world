package com.dante.network;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 代表一个IP地址，并且将IP地址和域名相关的操作方法包含在该类的内部
 * 使用InetAddress对象代表IP地址的构造方法
 * 
 * @author dante
 *
 */
public class InetAddressTest {
	
	public static void main(String[] args) throws UnknownHostException {
		InetAddress inet1 = InetAddress.getByName("www.163.com");
        System.out.println(inet1);
        
        InetAddress inet2 = InetAddress.getByName("boss.hnair.net");
        System.out.println(inet2);
        
        InetAddress local = InetAddress.getLocalHost();
        System.out.println(local);
	}
	
}
