package org.java.world.spring.spel;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfig {
	
	@Value("${db.password}")
	private String db1122Password;
	
	public void xx() {
		System.out.println(db1122Password);
	}
}
