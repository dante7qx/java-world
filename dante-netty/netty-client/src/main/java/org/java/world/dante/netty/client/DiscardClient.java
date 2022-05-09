package org.java.world.dante.netty.client;

import org.java.world.dante.netty.handler.DiscardClientHandler;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class DiscardClient {
	
	public void run(String host, int serverListenPort) throws InterruptedException {
		// 1. 创建工作线程池
		EventLoopGroup clientGroup = new NioEventLoopGroup(10);
		try {
			// 2. 创建客户端启动类 (Bootstrap 用于管理客户端的 Channel)
			Bootstrap bootstrap = new Bootstrap();
			// 3. 指定工作线程池
			// 4. 指定客户端 Channel 类型
			// 5. 指定任务处理器, 设置 pipeline
			// 6. 设置长连接
			bootstrap.group(clientGroup)
				.channel(NioSocketChannel.class)
				.handler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel channel) throws Exception {
						channel.pipeline().addLast(new DiscardClientHandler());
					}
				})
				.option(ChannelOption.SO_KEEPALIVE, true);
			// 7. 开启客户端连接(异步)
			ChannelFuture channelFuture = bootstrap.connect(host, serverListenPort).sync();
			// 8. 连接结束（服务器断开）后，关闭连接 Channel
			channelFuture.channel().closeFuture().sync();
		} finally {
			clientGroup.shutdownGracefully();
		}
	}
	
}
