package org.java.world.dante.serialiable.encoder;

import com.alibaba.fastjson.JSONObject;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class FastJsonEncoder extends MessageToByteEncoder<Object> {

	@Override
	protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf byteBuf) throws Exception {
		byte[] data = JSONObject.toJSONString(msg).getBytes();
		byteBuf.writeBytes(data);
	}

}
