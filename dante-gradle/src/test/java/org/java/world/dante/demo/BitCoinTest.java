package org.java.world.dante.demo;

import org.junit.jupiter.api.Test;

import cn.hutool.core.convert.NumberChineseFormatter;
import cn.hutool.core.lang.Console;

public class BitCoinTest {
	
	@Test
	public void basicType() {
		Long x1 = 100L;
		Long x2 = 100L;
		
		Console.log(x1 == x2);
		Console.log(x1.equals(x2));
		
		Long x3 = 129L;
		Long x4 = 129L;
		
		Console.log(x3 == x4);
		Console.log(x3.equals(x4));
		
		long s1 = 129L;
		long s2 = 129L;
		
		Console.log(s1 == s2);
		
		double amount = 12345.67;
        String chineseAmount = NumberChineseFormatter.format(amount, true, true);
        System.out.println(chineseAmount);
	}
	
	
	
}
