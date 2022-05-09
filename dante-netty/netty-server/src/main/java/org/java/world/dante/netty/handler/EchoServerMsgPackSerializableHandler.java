package org.java.world.dante.netty.handler;

import org.java.world.dante.vo.MsgPackVO;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EchoServerMsgPackSerializableHandler extends ChannelInboundHandlerAdapter {
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		try {
			// 1.读取 client 发送的信息
			MsgPackVO msgVO = (MsgPackVO) msg;
			System.err.println(msgVO);
			// 2. 返回给 client 的信息
			msgVO.setMsg("{ 服务器 } " + msgVO.getMsg());
			ctx.writeAndFlush(msgVO);
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
