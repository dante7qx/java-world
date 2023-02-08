package org.java.world.dante.netty.handler;

import org.java.world.dante.util.HostInfo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

public class EchoClientPackageHandler extends ChannelInboundHandlerAdapter {
	
	final int REPEAT = 1;	// 重复发送次数
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		for (int i = 0; i < REPEAT; i++) {
			byte[] data = ("{客户端} Say Hello! " + i + HostInfo.SEPARATOR_SYSTEM).getBytes();
			// 数据存入缓存
			ByteBuf buf = Unpooled.buffer(data.length);
			buf.writeBytes(data);
			// 发送数据
			ctx.writeAndFlush(buf);
		}
	}	
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		try {
			// 1. 获取服务端信息
			ByteBuf receiveBuf = (ByteBuf) msg;
			String receiveMsg = receiveBuf.toString(CharsetUtil.UTF_8);
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
