package com.dante.jdk8.string;

import java.util.StringJoiner;

import cn.hutool.core.codec.Base64;

public class StringJoinerTest {
	
	public static void main(String[] args) {
		
		String[] names = {"Bob", "Alice", "Grace"};
        var sj = new StringJoiner(", ", "Hello ", "!");
        for (String name : names) {
            sj.add(name);
        }
        System.out.println(sj.toString());
		
        var s = String.join(", ", names);
        System.out.println(s);
        
        String x = "hello %s";
        System.out.println(x.formatted("world!"));
        
        System.out.println(Base64.encode("hello".getBytes()));
	}

}
