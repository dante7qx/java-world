package com.dante.jdk10.collector;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * var 和 旧版本Lombok 有冲突，要升级Lombok
 * 
 * @author dante
 *
 */
public class CollectorTest {

	public static void main(String[] args) {
		var list = List.of("dante", "snake", "youna");
		if (list instanceof List) {
			System.out.println("List 类型");
		}
		System.out.println(list);

		var var = "ch.sun@haihangyun.com";
		System.out.println(var);

		var map = Map.of("key1", "val1", "key2", "val2", "key3", "val3");
		if(map instanceof HashMap) {
			System.out.println("HashMap 类型");
		} else if(map instanceof LinkedHashMap) {
			System.out.println("LinkedHashMap 类型");
		}
		System.out.println(map);
	}

}
