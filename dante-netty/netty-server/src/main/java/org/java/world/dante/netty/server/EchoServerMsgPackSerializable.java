package org.java.world.dante.netty.server;

import org.java.world.dante.netty.handler.EchoServerMsgPackSerializableHandler;
import org.java.world.dante.serialiable.decoder.MessagePackDecoder;
import org.java.world.dante.serialiable.encoder.MessagePackEncoder;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EchoServerMsgPackSerializable {
	
	public void run(int listenPort) throws InterruptedException {
		// 1. 创建线程池，主线程池（连接处理）、工作线程池（连接后业务处理）
		EventLoopGroup bossGroup = new NioEventLoopGroup(10);
		EventLoopGroup workerGroup = new NioEventLoopGroup(20);
		try {
			log.info("服务开始启动，监听端口：{}", listenPort);
			ServerBootstrap serverBootstrap = new ServerBootstrap();
			serverBootstrap.group(bossGroup, workerGroup)
				.channel(NioServerSocketChannel.class)
				.childHandler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel socketChannel) throws Exception {
						socketChannel.pipeline().addLast(new LengthFieldBasedFrameDecoder(65536, 0, 4, 0, 4));
						socketChannel.pipeline().addLast(new MessagePackDecoder());
						socketChannel.pipeline().addLast(new LengthFieldPrepender(4));
						socketChannel.pipeline().addLast(new MessagePackEncoder());
						socketChannel.pipeline().addLast(new EchoServerMsgPackSerializableHandler());
					}
				})
				.option(ChannelOption.SO_BACKLOG, 128)		// 设置连接块大小（TCP）
				.option(ChannelOption.SO_KEEPALIVE, true);	// 设置长连接	（TCP）;
			
			// 绑定监听端口，开始等待接收进来的连接
			ChannelFuture channelFuture = serverBootstrap.bind(listenPort).sync();
			// 等待服务器 socket 关闭 
			channelFuture.channel().closeFuture().sync();
		} finally {
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
	}
	
}
