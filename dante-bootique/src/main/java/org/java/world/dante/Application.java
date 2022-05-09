package org.java.world.dante;

import org.java.world.dante.config.DIModuleConfig;
import org.java.world.dante.resource.HelloResource;

import com.google.inject.Module;

import io.bootique.Bootique;
import io.bootique.jersey.JerseyModule;

/**
 * 参考：https://bootique.io/docs/1.x/getting-started/
 * 
 * @author dante
 *
 */
public class Application {
	public static void main(String[] args) {
		Module module = binder ->
				JerseyModule.extend(binder).addResource(HelloResource.class);
				
		Bootique
			.app(args)
			.modules(module, new DIModuleConfig())
			.autoLoadModules()
			.exec()
			.exit();
	}
}
