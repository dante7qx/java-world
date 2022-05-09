package org.java.world.dante.netty.handler;

import org.java.world.dante.util.HostInfo;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

public class EchoClientCustomerSeparatorHandler extends ChannelInboundHandlerAdapter {
	
	final int REPEAT = 50;	// 重复发送次数
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		for (int i = 0; i < REPEAT; i++) {
			// 发送数据
			ctx.writeAndFlush("{客户端} Say Hello! " + i + HostInfo.SEPARATOR_CUSTOMER);
		}
	}	
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		try {
			// 1. 获取服务端信息
			String receiveMsg = msg.toString().trim();
			System.err.println(receiveMsg);
		} finally {
			ReferenceCountUtil.release(msg);
		}
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
	
}
