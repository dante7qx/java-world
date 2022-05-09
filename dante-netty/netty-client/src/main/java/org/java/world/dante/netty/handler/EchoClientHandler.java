package org.java.world.dante.netty.handler;

import org.java.world.dante.util.InputUtil;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EchoClientHandler extends ChannelInboundHandlerAdapter {
	
	/*
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("==================> Echo Client 连接成功.");
		byte[] data = ("[客户端] " + Thread.currentThread().getId()).getBytes();
		// 数据存入缓存
		ByteBuf buf = Unpooled.buffer(data.length);
		buf.writeBytes(data);
		// 发送数据
		ctx.writeAndFlush(buf);
	}	
	*/
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		log.info("===========================> Echo Client 可读就绪.");
		try {
			// 1. 获取服务端信息
			ByteBuf receiveBuf = (ByteBuf) msg;
			String receiveMsg = receiveBuf.toString(CharsetUtil.UTF_8);
			if("quit".equalsIgnoreCase(receiveMsg)) {
				log.info("通话结束，服务器断开连接！");
				ctx.close();
			} else {
				log.info(receiveMsg);
				// 2. 进行通信 (数据写入缓存，从缓存中取数据发送)
				log.info("请用户输入消息：");
				String inputMsg = InputUtil.getUserInput();
				
				ByteBuf dataBuf = Unpooled.buffer(inputMsg.getBytes().length);
				dataBuf.writeBytes(inputMsg.getBytes());
				ctx.writeAndFlush(dataBuf);
			}
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
