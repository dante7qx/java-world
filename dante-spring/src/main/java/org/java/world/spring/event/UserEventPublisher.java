package org.java.world.spring.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * 使用容器发布事件
 * 
 * @author dante
 */
@Component
public class UserEventPublisher {

	@Autowired
	private ApplicationContext applicationContext;
	
	public void publish(String msg) {
		applicationContext.publishEvent(new UserEvent(this, msg));
	}
}
