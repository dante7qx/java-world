package org.java.world.dante.demo.guava;

import com.google.common.eventbus.Subscribe;

public class EventListener {

	/**
	 * 监听 Integer 类型的消息
	 */
	@Subscribe
	public void listenInteger(Integer param) {
		System.out.println("EventListener#listenInteger ->" + param);
	}

	/**
	 * 监听 String 类型的消息
	 */
	@Subscribe
	public void listenString(String param) {
		System.out.println("EventListener#listenString ->" + param);
	}

}
