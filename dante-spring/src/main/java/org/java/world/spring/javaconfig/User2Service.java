package org.java.world.spring.javaconfig;

import org.springframework.beans.factory.annotation.Autowired;

public class User2Service {

	@Autowired
	private User1Service user1Service;
	
	public String sayHello(String word) {
		return user1Service.sayHello(word);
	}
	
	public String sayYes(String name) {
		return "Yes " + name;
	}
	
}
