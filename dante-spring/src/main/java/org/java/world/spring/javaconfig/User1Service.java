package org.java.world.spring.javaconfig;

import org.springframework.stereotype.Service;

@Service
public class User1Service {

	public String sayHello(String word) {
		return "hello " + word;
	}

}
