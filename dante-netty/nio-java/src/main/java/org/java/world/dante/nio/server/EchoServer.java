package org.java.world.dante.nio.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.java.world.dante.util.HostInfo;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access=AccessLevel.PRIVATE)
public class EchoServer {
	
	// 创造线程池对各个 Channel 进行处理
	private static final ExecutorService executorService = Executors.newFixedThreadPool(4);
	
	public static void main(String[] args) throws IOException {
		
		// 1. 打开服务端 Socket 通道
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		// 2. 设置 Channel 为非阻塞模式
		serverSocketChannel.configureBlocking(false);
		// 3. 绑定监听端口 
		serverSocketChannel.bind(new InetSocketAddress(HostInfo.PORT));
		// 4. 开启一个 Selector，管理所有的 Channel
		Selector selector = Selector.open();
		// 5. 将当前 ServerSocketChannel 注册到 Selector 中，在有客户端连接时进行处理
		serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
		log.info("服务器已经启动，监听端口为 {}.", HostInfo.PORT);
		// 6. NIO 采用轮询模式，每当发现一个客户端连接就启动一个线程
		while (selector.select() > 0) {
			Set<SelectionKey> selectionKeys = selector.keys();
			Iterator<SelectionKey> keyItor = selectionKeys.iterator();
			while(keyItor.hasNext()) {
				SelectionKey selectionKey = keyItor.next();
				if(selectionKey.isConnectable()) {	// 连接完成（成功或失败）
					log.info(".................... isConnectable");
				} else if(selectionKey.isAcceptable()) {	// 可以开始连接
					log.info(".................... isAcceptable");
					SocketChannel clientChannel = serverSocketChannel.accept();	// 等待连接
					if(clientChannel != null) {
						executorService.submit(new EchoServerHandler(clientChannel));
					}
				} else if(selectionKey.isReadable()) {	// 可以开始读取
					log.info(".................... isReadable");
				} else if(selectionKey.isWritable()) {	// 可以开始写入
					log.info(".................... isWritable");
				}
				// 移动被处理的 SelectionKey
				keyItor.remove();
			}
		}
		executorService.shutdown();
		serverSocketChannel.close();
	}

}
