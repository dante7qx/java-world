package org.java.world.dante.netty.handler;

import org.java.world.dante.util.HostInfo;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EchoServerCustomerSeparatorHandler extends ChannelInboundHandlerAdapter {
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		try {
			// 1. 读取客户端信息
			String clientMsg = msg.toString().trim();
			System.err.println(clientMsg);
			// 2. 构造回复信息（写入缓存）
			String sendMsg = "{服务器} " + clientMsg + HostInfo.SEPARATOR_CUSTOMER;
			// 3. 从缓存中将数据发送
			ctx.writeAndFlush(sendMsg);
		} finally {
			ReferenceCountUtil.release(msg);
		}
		
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		log.error("发生错误", cause);
		ctx.close();	// 关闭连接
	}

}
