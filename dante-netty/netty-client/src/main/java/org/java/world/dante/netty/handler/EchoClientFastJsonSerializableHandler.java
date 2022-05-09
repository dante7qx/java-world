package org.java.world.dante.netty.handler;

import org.java.world.dante.util.VOBuilderUtil;
import org.java.world.dante.vo.UserVO;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

public class EchoClientFastJsonSerializableHandler extends ChannelInboundHandlerAdapter {
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		UserVO user = VOBuilderUtil.buildUserVO();
		ctx.writeAndFlush(user);
	}	
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		try {
			// 1. 获取服务端信息
			UserVO user = (UserVO) msg;
			System.err.println(user);
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
