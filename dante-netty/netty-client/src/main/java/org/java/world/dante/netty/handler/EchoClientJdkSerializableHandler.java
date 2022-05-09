package org.java.world.dante.netty.handler;

import java.util.List;

import org.java.world.dante.util.VOBuilderUtil;
import org.java.world.dante.vo.UserVO;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

public class EchoClientJdkSerializableHandler extends ChannelInboundHandlerAdapter {
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		List<UserVO> users = VOBuilderUtil.buildUserVOs();
		ctx.writeAndFlush(users);
	}	
	
	@Override
	@SuppressWarnings("unchecked")
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		try {
			// 1. 获取服务端信息
			List<UserVO> users = (List<UserVO>) msg;
			System.err.println(users);
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
