package com.dante.juc.readwritelock;


public class Reader implements Runnable {
	
	private Resource resource;
	
	public Reader(Resource resource) {
		this.resource = resource;
	}
	
	@Override
	public void run() {
		for (int i = 0; i < 4; i++) {
			try {
				resource.getDatas();
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
}
