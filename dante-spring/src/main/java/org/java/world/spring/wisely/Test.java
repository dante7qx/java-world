package org.java.world.spring.wisely;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Test {
	
	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(UserConfig.class);
		UserService userService = ctx.getBean(UserService.class);
		userService.print();
		
		ctx.close();
	}
	
}
