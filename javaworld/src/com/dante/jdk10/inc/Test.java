package com.dante.jdk10.inc;

public class Test {
	
	public static void main(String[] args) {
		InterfaceWithPrivateMethod impl = new InterfaceWithPrivateMethodImpl();
		impl.print();
		impl.defaultMethod();
	}
	
}
