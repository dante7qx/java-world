package com.dante.hutool;

import org.junit.jupiter.api.Test;

import cn.hutool.core.lang.Console;
import cn.hutool.core.thread.ConcurrencyTester;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.RandomUtil;

public class ThreadUtilTest {
	
	@Test
	public void concurrencyTest() {
		ConcurrencyTester tester = ThreadUtil.concurrencyTest(100, () -> {
		    // 测试的逻辑内容
		    long delay = RandomUtil.randomLong(100, 1000);
		    ThreadUtil.sleep(delay);
		    Console.log("{} test finished, delay: {}", Thread.currentThread().getName(), delay);
		});

		// 获取总的执行时间，单位毫秒
		Console.log(tester.getInterval());
	}
	
}
