package org.java.world.spring.fortest;

public class UserService {
	private String content;
	
	public UserService(String content) {
		this.content = content;
	} 
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getContent() {
		return content;
	}
}
