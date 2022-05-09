package com.dante.threadlocal;

public class TestClient implements Runnable {
	
	private SequenceNum sn;
	
	public TestClient(SequenceNum sn) {
		this.sn = sn;
	}
	
	@Override
	public void run() {
		for (int i = 0; i < 4; i++) {
			System.out.println("Thread["+Thread.currentThread().getName()+"], SN = " + sn.getNextNum());
		}
		
	}

}
