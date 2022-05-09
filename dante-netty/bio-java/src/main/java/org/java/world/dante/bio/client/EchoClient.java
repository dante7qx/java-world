package org.java.world.dante.bio.client;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

import org.java.world.dante.util.HostInfo;
import org.java.world.dante.util.InputUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EchoClient {
	
	public static void main(String[] args) throws IOException {
		// 1. 构造一个Client，和服务器建立连接.
		Socket client = new Socket(HostInfo.HOST, HostInfo.PORT);
		
		// 2. 获取服务端的响应信息
		Scanner serverResponse = new Scanner(client.getInputStream());
		serverResponse.useDelimiter("\n");
		
		// 3. 向服务端写数据
		PrintStream writeServer = new PrintStream(client.getOutputStream());
		
		// 4. 开始交互
		boolean flag = true;
		System.out.println("请用户输入消息：");
		while(flag) {
			// 2. 用户键盘输入
			String userInput = InputUtil.getUserInput();
			writeServer.println(userInput); // 发送数据
			
			if(serverResponse.hasNext()) {
				String respStr = serverResponse.next().trim();
				log.info("收到服务器响应：{}", respStr);
			}
			
			if("bye".equalsIgnoreCase(userInput)) {
				flag = false;
			}
		}
		
		serverResponse.close();
		client.close();
	}

}
