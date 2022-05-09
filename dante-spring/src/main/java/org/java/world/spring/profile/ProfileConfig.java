package org.java.world.spring.profile;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("org.java.world.spring.profile")
@PropertySource("classpath:org/java/world/spring/spel/test.properties")
public class ProfileConfig {
	
	@Bean
	@Profile("dev")
	public Datasource devDatasource() {
		Datasource source = new Datasource("dante", "123456");
		return source;
	}
	
	@Bean
	@Profile("prod")
	public Datasource prodDatasource() {
		Datasource source = new Datasource("root", "1w2e3r");
		return source;
	}
}
