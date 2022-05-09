package org.java.world.dante.nio.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import org.java.world.dante.util.HostInfo;
import org.java.world.dante.util.InputUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EchoClient {

	public static void main(String[] args) throws IOException {
		// 1. 开启一个客户端连接 Socket Channel
		SocketChannel clientChannel = SocketChannel.open();
		clientChannel.connect(new InetSocketAddress(HostInfo.HOST, HostInfo.PORT));
		// 2. 开辟缓冲区
		ByteBuffer buffer = ByteBuffer.allocate(50);
		boolean flag = true;
		System.out.println("请用户输入消息：");
		
		while(flag) {
			// 3. 将用户输入写到Buffer
			String userInput = InputUtil.getUserInput();
			buffer.clear();
			buffer.put(userInput.getBytes());
			// 切换写模式，
			buffer.flip();	
			clientChannel.write(buffer); // 向服务端发送数据
			
			// 4. 获取服务端数据
			buffer.clear();
			int readCount = clientChannel.read(buffer);
			String serverReplyMsg = new String(buffer.array(), 0, readCount);
			log.info(serverReplyMsg);
			if("bye".equals(userInput)) {
				flag = false;
			}
			// 切换写模式
			buffer.flip();
		}
		clientChannel.close();
	}
	
}
