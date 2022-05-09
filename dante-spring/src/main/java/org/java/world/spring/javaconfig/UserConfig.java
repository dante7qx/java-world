package org.java.world.spring.javaconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 全局配置使用Java配置（数据库、MVC配置）
 * 业务Bean使用注解配置
 * 
 * @author dante
 *
 */
@Configuration
@ComponentScan("org.java.world.spring.javaconfig")
public class UserConfig {

	@Bean
	public User2Service user2Service() {
		return new User2Service();
	}
	
	@Bean(initMethod = "init")
	public User3Service user3Service() {
		User3Service user3Service = new User3Service();
		user3Service.setUser2Service(user2Service());
		return user3Service;
	}

}
