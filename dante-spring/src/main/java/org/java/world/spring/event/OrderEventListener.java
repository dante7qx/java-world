package org.java.world.spring.event;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * 定义事件监听器，实现ApplicationListener
 * 
 * @author dante
 */
@Component
public class OrderEventListener implements ApplicationListener<UserEvent> {

	@Override
	public void onApplicationEvent(UserEvent event) {
		String msg = event.getMsg();
		
		System.out.println("OrderEventListener 收到 UserPublish 发布的消息：" + msg);
		
	}

}
