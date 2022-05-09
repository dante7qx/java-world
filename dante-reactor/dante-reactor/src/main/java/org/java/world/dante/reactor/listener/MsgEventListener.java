package org.java.world.dante.reactor.listener;

import org.java.world.dante.reactor.event.MsgEvent;

/**
 * 消息监听接口
 * 
 * @author dante
 *
 */
public interface MsgEventListener {
	
	/**
	 * 监听新事件触发
	 * 
	 * @param evt
	 */
	public void onNewEvent(MsgEvent evt);
	
	/**
	 * 监听事件停止
	 * 
	 */
	public void onEventStopped();
}
