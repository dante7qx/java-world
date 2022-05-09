package org.java.world.spring.fortest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class TestConfig {
	
	@Bean
	@Profile("dev")
	public UserService devUserService() {
		return new UserService("from dev profile.");
	}
	
	@Bean
	@Profile("prod")
	public UserService prodUserService() {
		return new UserService("from prod profile.");
	}
	
}
