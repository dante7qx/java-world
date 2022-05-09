package org.java.world.dante.netty.server;


import org.java.world.dante.netty.handler.DiscardServerHandler;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * NioEventLoopGroup 是用来处理I/O操作的多线程事件循环器, 
 * Netty 提供了许多不同的 EventLoopGroup 的实现用来处理不同的传输。
 * 
 * @author dante
 *
 */
@Slf4j
public class DiscardServer {
	
	public void run(int listenPort) throws InterruptedException {
		// 线程池是提升服务器性能的重要技术手段，利用定长的线程池可以保证核心线程的有效数量
        // 在 Netty 之中线程池的实现分为两类：主线程池（接收客户端连接）、工作线程池（处理客户端连接）
		EventLoopGroup bossGroup = new NioEventLoopGroup(4);	// 用于接收进来的连接，设置主线程池线程数量为4
		EventLoopGroup workerGroup = new NioEventLoopGroup(4);	// 用来处理已经被接收的连接，一旦‘boss’接收到连接，就会把连接信息注册到‘worker’上
		
		try {
			log.info("服务开始启动，监听端口：{}", listenPort);
			// 启动服务: 创建一个服务器端的程序类进行NIO启动，同时设置客户端的 Channel
			ServerBootstrap serverBootstrap = new ServerBootstrap();
			serverBootstrap.group(bossGroup, workerGroup)	// 设置用于连接（acceptor）和客户端处理（client）的线程池
				.channel(NioServerSocketChannel.class)		// 当前 channel 类型
				.childHandler(new ChannelInitializer<SocketChannel>() {	// 设置工作处理器，处理 SocketChannel（连接的客户端）
					@Override
					protected void initChannel(SocketChannel ch) throws Exception {
						ch.pipeline().addLast(new DiscardServerHandler());	// 追加处理器
					}
				}) 	
				.option(ChannelOption.SO_BACKLOG, 128)		// 设置连接块大小（TCP）
				.option(ChannelOption.SO_KEEPALIVE, true);	// 设置长连接	（TCP）
				
			// 绑定监听端口，开始接收进来的连接
			ChannelFuture channelFuture = serverBootstrap.bind(listenPort).sync();
			// 等待服务器 socket 关闭 
			channelFuture.channel().closeFuture().sync();
		} finally {
			// 关闭线程池
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
	}
	
}
