package org.java.world.spring.condition;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Test {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(
				ConditionConfig.class);
		ListService listService = ctx.getBean(ListService.class);
		
		System.out.println(ctx.getEnvironment().getProperty("os.name") + "下的列表命令是：" + listService.showListCmd());
		ctx.close();
	}
}
