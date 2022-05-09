package org.java.world.dante.reactor.event.source;

import java.util.ArrayList;
import java.util.List;

import org.java.world.dante.reactor.event.MsgEvent;
import org.java.world.dante.reactor.listener.MsgEventListener;

/**
 * 消息事件源
 * 
 * @author dante
 *
 */
public class MsgEventSource {
	
	private List<MsgEventListener> listeners;
	
	public MsgEventSource() {
		this.listeners = new ArrayList<>();
	}
	
	/**
	 * 注册监听器 MsgEventListener
	 * 
	 * @param listener
	 */
	public void register(MsgEventListener listener) {
		listeners.add(listener);
	}
	
	/**
	 * 向监听器发出新的事件
	 * 
	 * @param event
	 */
	public void newEvent(MsgEvent event) {
		this.listeners.forEach(l -> l.onNewEvent(event));
	}
	
	/**
	 * 通知监听器事件已经停止
	 */
	public void eventStopped() {
		this.listeners.forEach(MsgEventListener::onEventStopped);
	}
	
}
