package org.java.world.spring.concurrent.async;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Test {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx  = new AnnotationConfigApplicationContext(TaskExecutorConfig.class);
		
		AsyncMethodService methodService = ctx.getBean(AsyncMethodService.class);
		
		for (int i = 0; i < 10; i++) {
			methodService.executeAsyncTask(i);
			methodService.executeAsyncPlus(i);
		}
		
		ctx.close();
	}
}
