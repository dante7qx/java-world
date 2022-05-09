package org.java.world.dante.serialiable.decoder;

import java.util.List;

import org.java.world.dante.vo.MsgPackVO;
import org.msgpack.MessagePack;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

/**
 * MsgPack 解码器
 * 
 * @author dante
 *
 */
public class MessagePackDecoder extends MessageToMessageDecoder<ByteBuf>{

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> list) throws Exception {
		// 1. 读取传输数据
		int len = msg.readableBytes();	// 数据长度
		byte[] data = new byte[len];	// 创建数据容器
		msg.getBytes(msg.readerIndex(), data, 0, len);
		
		// 2. 将数据转化为对象list
		MessagePack msgPack = new MessagePack();
//		list.add(msgPack.read(data));	// 解码后是数组
		list.add(msgPack.read(data, msgPack.lookup(MsgPackVO.class)));
	}

}
