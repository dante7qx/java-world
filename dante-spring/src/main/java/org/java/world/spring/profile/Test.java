package org.java.world.spring.profile;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 1、通过设定Environment的ActiveProfiles设定当前context
 * 2、通过设定jvm的spring.profiles.active
 * 3、web项目设置Servlet的context paramter
 * 
 * @author dante
 *
 */
public class Test {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ctx.getEnvironment().setActiveProfiles("dev");
		ctx.register(ProfileConfig.class);
		ctx.refresh();
		
		Datasource datasource = ctx.getBean(Datasource.class);
		System.out.println(datasource);
		ctx.close();
	}
}
