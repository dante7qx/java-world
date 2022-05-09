package org.java.world.spring.wisely;

import org.springframework.stereotype.Service;

@Service
public class UserService {
	public void print() {
		System.out.println("自定义注解！");
	}
}
