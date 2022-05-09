package org.java.world.dante.netty.handler;

import org.java.world.dante.util.HostInfo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EchoServerPackageHandler extends ChannelInboundHandlerAdapter {
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		try {
			// 1. 读取客户端信息
			ByteBuf buf = (ByteBuf) msg;
			String clientMsg = buf.toString(CharsetUtil.UTF_8).trim();
			System.err.println(clientMsg);
			// 2. 构造回复信息（写入缓存）
			String sendMsg = "{服务器} " + clientMsg + HostInfo.SEPARATOR_SYSTEM;
			byte[] sendMsgData = sendMsg.getBytes();
			ByteBuf sendBuf = Unpooled.buffer(sendMsgData.length);
			sendBuf.writeBytes(sendMsgData);
			// 3. 从缓存中将数据发送
			ctx.writeAndFlush(sendBuf);
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
