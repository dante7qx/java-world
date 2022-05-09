package com.dante.jdk8.util;

import java.util.Objects;

import org.junit.Test;

public class ObjectUtils {
	
	@Test
	public void hash() {
		UserVO u1 = new UserVO(100, "dante", "但丁", 33);
		UserVO u2 = new UserVO(200, "dante", "祁钰", 23);
		
		System.out.println(u1.equals(u2));
		System.out.println("User1 hash -> " + Objects.hash(u1));
		System.out.println("User2 hash -> " + Objects.hash(u2));
		
		System.out.println(Objects.equals(u1, u2));
		System.out.println("User1 hashCode -> " + Objects.hashCode(u1));
		System.out.println("User2 hashCode -> " + Objects.hashCode(u2));
		
		System.out.println(Objects.hash(3));
		System.out.println(Objects.hash(4));
		
	}
	
}
