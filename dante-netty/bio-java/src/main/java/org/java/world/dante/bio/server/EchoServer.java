package org.java.world.dante.bio.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.java.world.dante.util.HostInfo;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access=AccessLevel.PRIVATE)
public class EchoServer {
	
	/**
	 * 系统的线程是有限的，Linux中一个线程就相当与一个进程
	 */
	public static final ExecutorService executorService = Executors.newFixedThreadPool(4);

	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(HostInfo.PORT);
			log.info("服务器端已经启动，监听的端口为: {}", HostInfo.PORT);
			boolean flag = true;
			while(flag) {
				Socket client = serverSocket.accept(); 	// 接收一个Client
				executorService.submit(new EchoServerHandler(client));	// 开始处理任务
			}
		} catch (IOException e) {
			log.error("Socket Server 建立失败", e);
		} finally {
			executorService.shutdown();
			try {
				serverSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
