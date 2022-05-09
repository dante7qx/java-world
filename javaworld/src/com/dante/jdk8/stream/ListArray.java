package com.dante.jdk8.stream;

import java.util.Arrays;
import java.util.stream.Stream;

import org.junit.Test;

public class ListArray {

	@Test
	public void listJoinDistinct() {
		String[] str1 = { "08:00", "09:00", "10:00", "18:00", "21:00" };
		String[] str2 = { "12:00", "09:00", "10:00", "11:00" };

		Stream.concat(Stream.of(str1), Stream.of(str2)) // 合并
				.distinct() // 去重
				.sorted() // 排序
				.peek(System.out::println).toArray(String[]::new);
	}

	@Test
	public void listJoinDistinctReduce() {
		String[] str1 = { "08:00", "09:00", "10:00", "18:00", "21:00" };
		String[] str2 = { "12:00", "09:00", "10:00", "11:00" };
		String[] temp = Stream.concat(Stream.of(str1), Stream.of(str2)) // 合并
				.distinct() // 去重
				.sorted() // 排序
				.reduce((a, b) -> { // 拼接
					a = a + "-" + b + "," + b;
					return a;
				}).get().toString().split(",");

		// 去掉垃圾数据
		Arrays.asList(temp).stream().filter(v -> v.contains("-")).peek(System.out::println)
				.toArray(String[]::new);
	}

}
