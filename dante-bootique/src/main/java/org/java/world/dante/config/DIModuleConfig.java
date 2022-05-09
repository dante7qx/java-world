package org.java.world.dante.config;

import org.java.world.dante.service.IHelloService;
import org.java.world.dante.service.impl.HelloService;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

public class DIModuleConfig extends AbstractModule {
	@Override
	protected void configure() {
		bind(IHelloService.class).to(HelloService.class).in(Scopes.SINGLETON);
	}
}
