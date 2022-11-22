package com.dante.jdk8.stream;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

import com.dante.jdk8.stream.vo.Person;

public class ListFilterArray {

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
		Arrays.asList(temp).stream().filter(v -> v.contains("-")).peek(System.out::println).toArray(String[]::new);
	}

	@Test
	public void distinctByKey() {
		Person p1 = new Person();
		p1.setId(10L);
		p1.setName("但丁");
		p1.setAge(37);
		Person p2 = new Person();
		p2.setId(11L);
		p2.setName("但丁");
		p2.setAge(32);
		Person p3 = new Person();
		p3.setId(12L);
		p3.setName("名都");
		p3.setAge(23);
		
		List<Person> persons = Arrays.asList(p1, p2, p3);
		System.out.println("过滤前：" + persons);
		List<Person> persons2 = persons.stream().filter(distinctByKey(Person::getName)).collect(Collectors.toList());
		System.out.println("过滤后：" + persons2);
	}

	public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
		Map<Object, Boolean> seen = new ConcurrentHashMap<>();
		return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
	}

}
