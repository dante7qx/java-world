package com.dante.hutool;

import org.junit.jupiter.api.Test;

import cn.hutool.core.util.IdUtil;

public class IdUtilTest {
	
	@Test
	public void testUUID() {
		System.out.println(IdUtil.fastSimpleUUID());
		System.out.println(IdUtil.fastUUID());
		System.out.println(IdUtil.randomUUID());
		System.out.println(IdUtil.simpleUUID());
	}
	
}
