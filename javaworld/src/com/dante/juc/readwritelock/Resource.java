package com.dante.juc.readwritelock;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Resource {
	private List<Integer> datas = new CopyOnWriteArrayList<Integer>(new Integer[]{3, 7});
	private ReadWriteLock lock = new ReentrantReadWriteLock();

	public List<Integer> getDatas() {
		lock.readLock().lock();
		try {
			System.out.println(Thread.currentThread().getName() + "在" + new Date() + "开始读取资源。" + datas);
			Thread.sleep((long) (Math.random() * 10000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.readLock().unlock();
		}
		return datas;
	}

	public void addDatas(int data) {
		lock.writeLock().lock();
		try {
			this.datas.add(data);
			System.out.println(Thread.currentThread().getName() + "在" + new Date() + "开始修改资源。" + datas);
			Thread.sleep((long) (Math.random() * 10000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.writeLock().unlock();
		}

	}
}
