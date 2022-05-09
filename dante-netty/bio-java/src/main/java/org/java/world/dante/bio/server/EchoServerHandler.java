package org.java.world.dante.bio.server;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

import lombok.extern.slf4j.Slf4j;

/**
 * 客户端 Echo 请求处理类, 一个线程处理一个客户端
 * 
 * @author dante
 *
 */
@Slf4j
public class EchoServerHandler implements Runnable {

	private Socket client; // 客户端，每一个客户端都需要启动一个任务(task)来执行。
	private Scanner scanner; // 获取客户端的输入Input
	private PrintStream out; // 客户端的响应 Out
	private boolean flag = true;

	public EchoServerHandler(Socket client) {
		this.client = client;
		try {
			this.scanner = new Scanner(this.client.getInputStream());
			this.scanner.useDelimiter("\n"); // 设置换行符
			this.out = new PrintStream(this.client.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (this.flag) {
			if (this.scanner.hasNext()) {
				String receviceStr = this.scanner.next();
				log.info("[服务端{}, 收到 [{}] 发送的数据 [{}]]", Thread.currentThread().getId(),
						this.client.getRemoteSocketAddress(), receviceStr);
				if ("bye".equals(receviceStr)) {
					this.out.println("服务端结束通话。");
					this.flag = false;
				} else {
					this.out.println("[Echo] " + receviceStr);
				}
			}
		}
		this.scanner.close();
		this.out.close();
		try {
			this.client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
