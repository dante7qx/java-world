package com.dante.juc;

import java.util.concurrent.Exchanger;

/**
 *  Exchanger 一般用于两个工作线程之间交换数据。
 *  
 *  当一个线程到达exchange调用点时，如果它的伙伴线程此前已经调用了此方法，那么它的伙伴会被调度唤醒并与之进行对象交换，然后各自返回。
 *  如果它的伙伴还没到达交换点，那么当前线程将会被挂起，直至伙伴线程到达——完成交换正常返回；或者当前线程被中断——抛出中断异常；又或者是等候超时——抛出超时异常。
 * 
 * @author dante
 *
 */
public class ExchangerTest {
	
	public static void main(String[] args) {
		Exchanger<String> exchanger = new Exchanger<String>();
		new Thread(new Spy("间谍1", "我要炸白宫！", exchanger)).start();
		new Thread(new Spy("间谍2", "我要炸天安门！", exchanger)).start();
	}
}


class Spy implements Runnable {
	private String name;
	private String info;
	private Exchanger<String> exchanger;
	
	public Spy (String name, String info, Exchanger<String> exchanger) {
		this.name = name;
		this.info = info;
		this.exchanger = exchanger;
	}
	
	@Override
	public void run() {
		try {
			String oldInfo = this.info;
			String newInfo = exchanger.exchange(info);
			System.out.println(this.name + "用【" + oldInfo + "】交换的新情报是：【" + newInfo + "】");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}