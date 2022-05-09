package org.java.world.spring.javaconfig;

public class User3Service {
	private User2Service user2Service;

	public void init() {
		System.out.println("@Bean init method");
	}
	
	public String sayYes(String name) {
		return user2Service.sayYes(name);
	}

	public void setUser2Service(User2Service user2Service) {
		this.user2Service = user2Service;
	}
}
