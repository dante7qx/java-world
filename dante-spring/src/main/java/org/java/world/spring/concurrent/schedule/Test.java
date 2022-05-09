package org.java.world.spring.concurrent.schedule;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Test {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		new AnnotationConfigApplicationContext(TaskSchedulerConfig.class);

	}

}
