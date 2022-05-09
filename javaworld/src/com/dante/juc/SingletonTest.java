package com.dante.juc;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * https://www.cnblogs.com/xz816111/p/8470048.html
 * 
 * @author dante
 *
 */
public class SingletonTest {

	private volatile static SingletonTest instance;

	private SingletonTest() {

	}

	/**
	 * 双重锁校验
	 * 
	 * @return
	 */
	public static SingletonTest getInstance() {
		if (null != instance) return instance;

//		instance = new SingletonTest();
		
		synchronized (SingletonTest.class) {
			if (null == instance) {
				instance = new SingletonTest();
			}
		}
		return instance;
	}

	public static void main(String[] args) {
		List<String> list = new ArrayList<>();
		
		IntStream.range(1, 200).parallel().forEach(i -> {
			String str = System.identityHashCode(SingletonTest.getInstance()) + "";
			System.out.println(str);
			list.add(str);
		});

		List<String> uniqueList = list.stream().distinct().collect(Collectors.toList());
		System.out.println("总数：" + list.size());
		System.out.println("去重后数：" + uniqueList.size());

	}

}
