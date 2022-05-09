package org.java.world.dante.k8s;

import org.junit.Before;

import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.ConfigBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;

public class BaseTests {

	
	protected KubernetesClient client;
	
	protected static final String NAMESPACE = "dante";
	
	@Before
	public void initK8sClient() {
		Config config = new ConfigBuilder().build();
		
		client = new DefaultKubernetesClient(config);
	}
	
}
