package org.java.world.spring.event;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Test {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(EventConfig.class);
		UserEventPublisher publisher = ctx.getBean(UserEventPublisher.class);
		publisher.publish("已创建订单！");
		ctx.close();
	}
}
