package org.java.world.spring.aware;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Test {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AwareConfig.class);
		
		AwareService service = ctx.getBean(AwareService.class);
		
		service.print();
		
		ctx.close();
	}
}
