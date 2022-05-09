package com.dante.jdk8.stream;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.dante.jdk8.stream.annotation.Author;
import com.dante.jdk8.stream.vo.Person;

public class Hello {

	public static void main(String[] args) {
		List<String> output = Stream.of("a", "b", "c").map(String::toUpperCase).collect(Collectors.toList());
		System.out.println(output);
		
		System.out.println(factorialTailRecursive(6));
		
		System.out.println(Person.class.getAnnotationsByType(Author.class));
		
		LongAdder adder = new LongAdder();
		adder.add(10L);
		adder.add(30L);
		System.out.println(adder.sum());
		
		String joins = String.join(", ", "hello", "java8", "world");
		System.out.println("joins: " + joins);
		
		// 匿名内部类写法
		new File(".").listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				return pathname.isHidden();
			}
		});
		
		// Lambda写法
		new File(".").listFiles(File::isHidden);
		
		// 提取单词中唯一的字母
		List<String> words = Arrays.asList("hello","world");
		List<String> singles = words.stream()
		  .map(w -> w.split(""))
		  .flatMap(Arrays::stream)
		  .distinct()
		  .collect(Collectors.toList());
		System.out.println(singles);
		
		// 返回总和能被3整除的数对
		List<Integer> numbers1 = Arrays.asList(1, 2, 3);
		List<Integer> numbers2 = Arrays.asList(3, 4);
		List<int[]> pairs =
		  numbers1.stream()
		  .flatMap(i -> numbers2.stream()
		           .filter(j -> (i + j) % 3 == 0)
		           .map(j -> new int[]{i, j})
		).collect(Collectors.toList());
		System.out.println(pairs);
		
		// 字符串拼接
		Optional<String> concatStr = Stream.of("A", "B", "C", "D").reduce(String::concat);
		concatStr.ifPresent(str -> System.out.println(str));
		
		String[] x = {"1", "2", "3"};
        int sum = Arrays.stream(x).mapToInt(Integer::parseInt).sum();
        System.out.println(sum);
        
	}
	
	static long factorialTailRecursive(long n) {
	    return factorialHelper(1, n);
	}
	static long factorialHelper(long acc, long n) {
	    return n == 1 ? 1 : factorialHelper(acc * n, n - 1);
	}
	
}
