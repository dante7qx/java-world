package org.java.world.dante.demo.guava;

import org.junit.jupiter.api.Test;

import com.google.common.eventbus.EventBus;

public class TestEventBus {
	
	@Test
	public void testEventBus() {
		EventBus eventBus = new EventBus();

		eventBus.register(new EventListener());

		eventBus.post(1);
		eventBus.post(2);
		eventBus.post("3");

	}
	
}
