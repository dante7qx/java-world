package com.dante.juc;

/**
 * 死锁
 * 
 * @author dante
 *
 */
public class ResolveDeadLockTest {

	public static void main(String[] args) {
		ResolveDeadLockTest test = new ResolveDeadLockTest();

		final A a = test.new A();
		final B b = test.new B();

		// Thread-1
		Runnable block1 = new Runnable() {
			public void run() {
				synchronized (a) {
					try {
						// 设置延迟，模拟两个线程都能够去尝试锁定资源
						System.out.println("block1 Fetch resource A -> " + a.getI());
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					// Thread-1 拥有 A 并且需要拥有 B
					synchronized (b) {
						System.out.println("In block 1 -> " + b.getI());
					}
				}
			}
		};

		// Thread-2
		Runnable block2 = new Runnable() {
			public void run() {
				// 模拟死锁
				synchronized (b) {
					System.out.println("block2 Fetch resource B -> " + b.getI());
					// Thread-2 拥有 B 并且需要拥有 A
					synchronized (a) {
						System.out.println("In block 2 -> " + a.getI());
					}
				}
				// 解决死锁。访问资源 A 和 B 的模式是主要问题。 因此，为了解决这个问题，简单地重新排序代码访问共享资源的语句
				/*
				synchronized (a) {
					System.out.println("block2 Fetch resource B");
					// Thread-2 拥有 B 并且需要拥有 A
					synchronized (b) {
						System.out.println("In block 2");
					}
				}
				*/
			}
		};

		new Thread(block1).start();
		new Thread(block2).start();
	}

	// Resource A
	private class A {
		private int i = 10;

		public int getI() {
			return i;
		}

		public void setI(int i) {
			this.i = i;
		}
	}

	// Resource B
	private class B {
		private int i = 20;

		public int getI() {
			return i;
		}

		public void setI(int i) {
			this.i = i;
		}
	}

}
