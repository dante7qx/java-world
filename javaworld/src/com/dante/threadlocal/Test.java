package com.dante.threadlocal;

public class Test {
	private static SequenceNum sn = new SequenceNum();
	
	public static void main(String[] args) {
		
		Thread client1 = new Thread(new TestClient(sn));
		Thread client2 = new Thread(new TestClient(sn));
		Thread client3 = new Thread(new TestClient(sn));
		
		client1.start();
		client2.start();
		client3.start();
	}

}
