package org.java.world.mapstruct.vo;

public class Test {
	public static void main(String[] args) {
		
		SubVO1 sub1 = new SubVO1("123");
		sub1.setId(100L);
		
		SubVO1 sub2 = new SubVO1("123");
		sub2.setId(200L);
		
		System.out.println(sub1.equals(sub2));
		
		OriginVO oVO1 = new OriginVO();
		oVO1.setId(100L);
		oVO1.setName("无尽的夜");
		
		OriginVO oVO2 = new OriginVO();
		oVO2.setId(100L);
		oVO2.setName("无尽的夜");
		
		System.out.println(oVO1.equals(oVO2));
		
	}
}
