package org.java.world.spring.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.lang.NonNull;

/**
 * 自定义事件，继承ApplicationEvent
 * 
 * @author dante
 */
public class UserEvent extends ApplicationEvent {
	private static final long serialVersionUID = 1L;

	private String msg;

	public UserEvent(Object source, @NonNull String msg) {
		super(source);
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
