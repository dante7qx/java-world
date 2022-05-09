package org.dante.springmvc.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@ComponentScan("org.dante.springmvc")
@PropertySource(value = { "classpath:org/dante/springmvc/spring/app.properties",
		"classpath:org/dante/springmvc/spring/jdbc.properties" })
public class PlaceholderConfig {
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigure() {
		return new PropertySourcesPlaceholderConfigurer();
	}
}
