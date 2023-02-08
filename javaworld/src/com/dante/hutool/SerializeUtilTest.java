package com.dante.hutool;

import java.util.List;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Console;
import cn.hutool.core.util.SerializeUtil;

public class SerializeUtilTest {

	private static byte[] serial1 = null;
	private static byte[] serial2 = null;

	@Order(1)
	@Test
	public void serialize() {
		List<String> list = CollUtil.newArrayList("11", "22", "33");
		serial1 = SerializeUtil.serialize("你好");
		serial2 = SerializeUtil.serialize(list);
	}

	@Order(2)
	@Test
	public void derialize() {
		Object deser1 = SerializeUtil.deserialize(serial1);
		Object deser2 = SerializeUtil.deserialize(serial2);
		Console.log(deser1);
		Console.log(deser2);
	}

}
