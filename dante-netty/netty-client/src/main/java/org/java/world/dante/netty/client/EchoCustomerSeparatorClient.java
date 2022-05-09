package org.java.world.dante.netty.client;

import org.java.world.dante.netty.handler.EchoClientCustomerSeparatorHandler;
import org.java.world.dante.util.HostInfo;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

public class EchoCustomerSeparatorClient {
	
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
						socketChannel.pipeline().addLast(new FixedLengthFrameDecoder(100));	// 每次传输的文件块
//						socketChannel.pipeline().addLast(new LineBasedFrameDecoder(1024));	// (系统默认数据包分隔符) 拆包与粘包器
						socketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, Unpooled.copiedBuffer(HostInfo.SEPARATOR_CUSTOMER.getBytes())));  // (自定义数据包分隔符) 拆包与粘包器
						socketChannel.pipeline().addLast(new StringEncoder(CharsetUtil.UTF_8));
						socketChannel.pipeline().addLast(new StringDecoder(CharsetUtil.UTF_8));
						socketChannel.pipeline().addLast(new EchoClientCustomerSeparatorHandler());
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
