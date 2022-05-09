package org.java.world.dante.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 向服务端发送消息，服务端不进行返回
 * 
 * @author dante
 *
 */
@Slf4j
public class DiscardClientHandler extends ChannelInboundHandlerAdapter {

	/**
	 * 连接后，向服务端发送一条消息
	 */
	/*
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		log.info("===========================> Discard Client 连接成功.");
		byte[] data = ("[客户端] " + Thread.currentThread().getId()).getBytes();
		// 数据存入缓存
		ByteBuf buf = Unpooled.buffer(data.length);
		buf.writeBytes(data);
		// 发送数据
		ctx.writeAndFlush(buf);
	}
	*/
	
	/**
	 * 每隔 10 秒发送
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		log.info("===========================> Discard Client 可读就绪.");
		try {
			byte[] data = ("[客户端] " + Thread.currentThread().getId()).getBytes();
			// 数据存入缓存
			ByteBuf buf = Unpooled.buffer(data.length);
			buf.writeBytes(data);
			// 发送数据
			ctx.writeAndFlush(buf);
			// 主动关闭连接
			Thread.sleep(10000L);
			ctx.close(); 
		} finally {
			ReferenceCountUtil.release(msg);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		log.info("===========================> Discard Client 连接失败.");
		super.channelInactive(ctx);
	}

	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		log.info("===========================> Discard Client 注册成功.");
		super.channelRegistered(ctx);
	}

	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
		log.info("===========================> Discard Client 注册失效.");
		super.channelUnregistered(ctx);
	}

	@Override
	public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
		log.info("===========================> Discard Client WritabilityChanged.");
		super.channelWritabilityChanged(ctx);
	}

}
