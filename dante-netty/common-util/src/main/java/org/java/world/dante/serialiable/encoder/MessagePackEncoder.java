package org.java.world.dante.serialiable.encoder;

import org.msgpack.MessagePack;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * MsgPack 编码器
 * 
 * @author dante
 *
 */
public class MessagePackEncoder extends MessageToByteEncoder<Object> {

	@Override
	protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf byteBuf) throws Exception {
		// 1. 将传输对象进行编码
		MessagePack msgPack = new MessagePack();
		byte[] raw = msgPack.write(msg);
		// 2. 将编码后的数据写入 Buf 缓存
		byteBuf.writeBytes(raw);
	}

}
