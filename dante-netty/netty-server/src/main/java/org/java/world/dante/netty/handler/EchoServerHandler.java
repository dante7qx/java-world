package org.java.world.dante.netty.handler;

import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EchoServerHandler extends ChannelInboundHandlerAdapter {
	
	/**
	 * 当客户端连接成功后会进行此方法的调用
	 * 
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		byte[] data = "[服务激活信息] 连接通道已经创建，服务器开始进行响应交互。".getBytes();
		// Netty 对 NIO 的缓存进行了封装
		ByteBuf buf = Unpooled.buffer(data.length);	// 开辟了一块缓存空间
		buf.writeBytes(data); // 数据写入缓存中
		ctx.writeAndFlush(buf); // 发送数据
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		try {
			// 1. 读取客户端信息
			ByteBuf buf = (ByteBuf) msg;
			String clientMsg = buf.toString(Charset.forName("utf-8")).trim();
			log.info("Client==> {}", clientMsg);
			// 2. 构造回复信息（写入缓存）
			String sendMsg = "[Echo] " + clientMsg + "\n";
			if("exit".equalsIgnoreCase(clientMsg)) {
				log.info("【Exit】交谈结束。");
				sendMsg = "quit";	// 客户端退出连接标示
			}
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
