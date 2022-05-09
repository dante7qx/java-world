package org.java.world.spring.aop;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Test {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AopConfig.class);
		
		DemoAnnotationService demoAnnotationService = ctx.getBean(DemoAnnotationService.class);
		DemoMethodService demoMethodService = ctx.getBean(DemoMethodService.class);
		
		demoAnnotationService.add();
		demoMethodService.add();
		
		ctx.close();
	}
	
}
