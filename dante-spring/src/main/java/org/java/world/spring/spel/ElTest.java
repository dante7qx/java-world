package org.java.world.spring.spel;

import java.io.IOException;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ElTest {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(ElConfig.class, TestConfig.class);
		
		try {
			ElConfig config = ctx.getBean(ElConfig.class);
			config.outputResource();
			
			TestConfig x = ctx.getBean(TestConfig.class);
			x.xx();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ctx.close();
	}
}
