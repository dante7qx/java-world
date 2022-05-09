package com.dante.juc.readwritelock;

public class Writer implements Runnable {

	private Resource resource;

	public Writer(Resource resource) {
		this.resource = resource;
	}

	@Override
	public void run() {
		try {
			Thread.sleep(3000);
			resource.addDatas((int) (100 * Math.random()));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
