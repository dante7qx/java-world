package com.dante.juc.readwritelock;

public class ReadWriteLockTest {

	public static void main(String[] args) {
		Resource resource = new Resource();

		// 3个修改者
		for (int i = 0; i < 3; i++) {
			new Thread(new Writer(resource)).start();
		}

		// 20个阅读者
		for (int i = 0; i < 20; i++) {
			new Thread(new Reader(resource)).start();
		}
	}

}
