package org.java.world.spring.aop;

import org.springframework.stereotype.Service;

@Service
public class DemoAnnotationService {
	
	@Actional(name = "注解式拦截的add操作")
	public void add() {
	}
}
