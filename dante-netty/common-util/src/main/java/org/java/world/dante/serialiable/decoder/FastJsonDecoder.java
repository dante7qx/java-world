package org.java.world.dante.serialiable.decoder;

import java.util.List;

import org.java.world.dante.vo.UserVO;

import com.alibaba.fastjson.JSON;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

public class FastJsonDecoder extends MessageToMessageDecoder<ByteBuf> {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> list) throws Exception {
		int len = msg.readableBytes();
		byte[] data = new byte[len];
		msg.getBytes(msg.readerIndex(), data, 0, len); // 从缓存中读数据
		list.add(JSON.parseObject(new String(data)).toJavaObject(UserVO.class));
	}

}
