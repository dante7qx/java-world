package org.java.world.dante.nio;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.java.world.dante.vo.MsgPackVO;
import org.junit.Test;
import org.msgpack.MessagePack;
import org.msgpack.template.Templates;

public class MsgPackTests {

	@Test
	public void msgPackSerializable() throws IOException {
		List<MsgPackVO> msgs = new ArrayList<>();
		MsgPackVO msg = new MsgPackVO();
		msg.setUid("M-1001");
		msg.setMsg("MsgPack 信息！");
		msg.setCode(1000001);
		msg.setSendDate(Date.from(Instant.now()));
		msgs.add(msg);
		
		MessagePack msgPack = new MessagePack();
		// 编码工作
		byte[] raw = msgPack.write(msgs);
		// 使用模板对象进行解码操作
		List<MsgPackVO> readList = msgPack.read(raw, Templates.tList(msgPack.lookup(MsgPackVO.class)));
		System.err.println(readList);
	}
	
}
