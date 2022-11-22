package com.dante.hutool;

import org.junit.jupiter.api.Test;

import cn.hutool.core.util.RuntimeUtil;

public class RuntimeUtilTest {
	
	@Test
	public void test() {
		String str = RuntimeUtil.execForStr("pwd", "mkdir aa");
		System.out.println(str);
		
	}
}
