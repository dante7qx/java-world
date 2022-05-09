package org.java.world.spring.javaconfig;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Test {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ctx.register(UserConfig.class);
		ctx.refresh();
		
		User2Service user2Service = ctx.getBean(User2Service.class);
		System.out.println(user2Service.sayHello("world！"));
		
		User3Service user3Service = ctx.getBean(User3Service.class);
		System.out.println(user3Service.sayYes("Sir！"));
		
		ctx.close();
	}
	
}
