package com.dante.hutool;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import cn.hutool.core.lang.Console;
import cn.hutool.core.text.StrSplitter;
import cn.hutool.core.thread.ConcurrencyTester;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.RuntimeUtil;

public class IdUtilTest {

	@Test
	public void testUUID() {
		System.out.println(IdUtil.fastSimpleUUID());
		System.out.println(IdUtil.fastUUID());
		System.out.println(IdUtil.randomUUID());
		System.out.println(IdUtil.simpleUUID());
	}

	@Test
	public void jucTest() {
		int count = RuntimeUtil.getProcessorCount() * 2;
		ConcurrencyTester tester = ThreadUtil.concurrencyTest(count, () -> {
			// 测试的逻辑内容
			long delay = RandomUtil.randomLong(100, 1000);
			ThreadUtil.sleep(delay);
			Console.log("{} test finished, delay: {}", Thread.currentThread().getName(), delay);
		});

		// 获取总的执行时间，单位毫秒
		Console.log(tester.getInterval());
	}

	@Test
	public void spilt() {
		String str1 = "a, ,efedsfs,   ddf";
		// 参数：被切分字符串，分隔符逗号，0表示无限制分片数，去除两边空格，忽略空白项
		List<String> list1 = StrSplitter.split(str1, ',', 0, true, true);
		List<String> list2 = Arrays.asList(str1.split(","));
		Console.log("{} - {}", list1, list2);
	}

}
