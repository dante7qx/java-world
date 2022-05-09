package com.dante.juc;

public class VolatileTest {
	
	protected static volatile boolean change = false;
	
	public static void main(String[] args) throws InterruptedException {
		new Thread() {
			@Override  
            public void run() {  
				for (;;) { 
	                if(change) {
	                	System.out.println("1111111111");
	                	System.exit(0); 
	                }
				}
            }  
		}.start();
		
		Thread.sleep(1);
		
		new Thread() {
			@Override  
            public void run() { 
				change = true;
                if(change) {
                	System.out.println("22222222222");
                }
            }  
		}.start();
	}
}
