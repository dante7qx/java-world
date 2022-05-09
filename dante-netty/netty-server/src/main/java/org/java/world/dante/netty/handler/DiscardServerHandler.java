package org.java.world.dante.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 服务端处理渠道 - Handles a server-side channel
 * 
 * @author dante
 *
 */
@Slf4j
public class DiscardServerHandler extends ChannelInboundHandlerAdapter {
	
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

	/**
	 * DISCARD 协议, 处理器忽略收到的消息
	 * 
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		try {
			ByteBuf buf = (ByteBuf) msg;
			
			if(buf != null) {
				log.info("收到消息 ===》{}", buf.toString(CharsetUtil.UTF_8));
			}
		} finally {
			// 消息被处理后，必须释放缓存区
			ReferenceCountUtil.release(msg);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		cause.printStackTrace();
		// you might want to send a response message with an error code 
		ctx.close();
	}

}
