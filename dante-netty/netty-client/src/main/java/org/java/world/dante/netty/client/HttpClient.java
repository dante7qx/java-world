package org.java.world.dante.netty.client;

import java.net.URI;
import java.net.URISyntaxException;

import org.java.world.dante.netty.handler.HttpClientHandler;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpVersion;

public class HttpClient {
	
	public void run(String host, int port, String url) throws InterruptedException {
		EventLoopGroup clientGroup = new NioEventLoopGroup(5);
		try {
			Bootstrap bootstrap = new Bootstrap();
	        bootstrap.group(clientGroup).channel(NioSocketChannel.class).option(ChannelOption.SO_KEEPALIVE, true)
	                .handler(new ChannelInitializer<SocketChannel>() {
	                    @Override
	                    protected void initChannel(SocketChannel sc) throws Exception {
	                        // HttpClientCodec 相当于同时添加了HttpResponseDecoder和HttpRequestEncoder
	                        sc.pipeline().addLast(new HttpClientCodec());
	                        sc.pipeline().addLast(new HttpClientHandler());
	                    }
	                });
	        Channel channel = bootstrap.connect(host, port).sync().channel();

	        DefaultFullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, new URI(url).toASCIIString());
	        
	        request.headers().set(HttpHeaderNames.HOST, host);
	        request.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderNames.CONNECTION);
	        request.headers().set(HttpHeaderNames.CONTENT_LENGTH, request.content().readableBytes());

	        channel.writeAndFlush(request).sync();
	        channel.closeFuture().sync();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} finally {
			clientGroup.shutdownGracefully();
		}
	}
}
