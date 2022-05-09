package org.java.world.dante.netty.handler;

import org.java.world.dante.vo.UserVO;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EchoServerFastJsonSerializableHandler extends ChannelInboundHandlerAdapter {
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		try {
			// 1. 读取客户端信息
			UserVO user = (UserVO) msg;
			System.err.println(user);
			user.setName("{服务端} " + user.getName());
			
			// 2. 构造回复信息（写入缓存）
			ctx.writeAndFlush(user);
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
