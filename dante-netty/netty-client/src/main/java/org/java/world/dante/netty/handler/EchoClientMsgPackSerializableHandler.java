package org.java.world.dante.netty.handler;

import org.java.world.dante.util.VOBuilderUtil;
import org.java.world.dante.vo.MsgPackVO;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

public class EchoClientMsgPackSerializableHandler extends ChannelInboundHandlerAdapter {
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		MsgPackVO msgVO = VOBuilderUtil.buildMsgPackVO();
		ctx.writeAndFlush(msgVO);
	}	
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		try {
			// 1. 获取服务端信息
			MsgPackVO msgVOs = (MsgPackVO) msg;
			System.err.println(msgVOs);
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
