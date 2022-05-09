package org.java.world.dante.netty.client;

import org.java.world.dante.netty.handler.EchoClientJdkSerializableHandler;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

public class EchoJdkSerializableClient {
	
	/**
	 * Echo Netty Client
	 * 
	 * @param host	服务端地址
	 * @param port	服务端监听端口
	 * @throws InterruptedException 
	 */
	public void run(String host, int port) throws InterruptedException {
		// 1、如果现在客户端不同，那么也可以不使用多线程模式来处理;
        // 在Netty中考虑到代码的统一性，也允许你在客户端设置线程池
		EventLoopGroup clientGroup = new NioEventLoopGroup(5);
		try {
			// 客户端启动程序
			Bootstrap client = new Bootstrap();
			client.group(clientGroup)
				.channel(NioSocketChannel.class)
				.handler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel socketChannel) throws Exception {
						// 使用原生 Jdk 的序列化进行对象序列化传输
						socketChannel.pipeline().addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(this.getClass().getClassLoader())));
						socketChannel.pipeline().addLast(new ObjectEncoder());
						socketChannel.pipeline().addLast(new EchoClientJdkSerializableHandler());
					}
				})
				.option(ChannelOption.SO_KEEPALIVE, true)
				.option(ChannelOption.TCP_NODELAY, true); // 允许接收大块的返回数据
			ChannelFuture channelFuture = client.connect(host, port).sync();
			channelFuture.channel().closeFuture().sync();	// 关闭连接
		} finally {
			clientGroup.shutdownGracefully();
		}
	}
}
