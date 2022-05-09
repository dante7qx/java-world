package org.java.world.dante.k8s;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import io.fabric8.kubernetes.api.model.Namespace;
import io.fabric8.kubernetes.api.model.NamespaceBuilder;
import io.fabric8.kubernetes.api.model.apps.Deployment;
import io.fabric8.kubernetes.api.model.apps.DeploymentBuilder;
import io.fabric8.kubernetes.client.KubernetesClientException;
import io.fabric8.kubernetes.client.Watch;
import io.fabric8.kubernetes.client.Watcher;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DeploymentTest extends BaseTests {
	
	@Test
	public void namespace() {
		// 查找 namespace
		Namespace space = client.namespaces().withName(NAMESPACE).get();
		log.info("Find namespace {}", space);
		if(space != null) return;
		Namespace ns = new NamespaceBuilder()
				.withNewMetadata()
				.withName(NAMESPACE)
				.addToLabels("app", "test")
				.endMetadata()
				.build();
		Namespace namespace = client.namespaces().createOrReplace(ns);	
		log.info("Created Or Replace namespace {}", namespace);
	}
	
	@Test
	public void applyDeployment() {
        // 创建 Deployment
        Deployment deployment = new DeploymentBuilder()
                .withNewMetadata()
                .withName("psp-tomcat")
                .endMetadata()
                .withNewSpec()
                .withReplicas(1)
                .withNewTemplate()
                .withNewMetadata()
                .addToLabels(new HashMap<String, String>() {
					private static final long serialVersionUID = 1L;
					{
                        put("app", "psp-web");
                        put("version", "v1.0");
                    }
                })
                .endMetadata()
                .withNewSpec()
	                .addNewContainer()
		                .withName("psp-tomcat")
		                .withImage("tomcat:8.5.60")
		                .addNewPort()
		                	.withContainerPort(8080)
		                	.endPort()
	                .endContainer()
                .endSpec()
                .endTemplate()
                .withNewSelector()
                	.addToMatchLabels("app", "psp-web")
                	.addToMatchLabels("version", "v1.0")
                .endSelector()
                .endSpec()
                .build();
        deployment = client.apps().deployments().inNamespace(NAMESPACE).createOrReplace(deployment);
        log.info("Created Or Replace deployment {}", deployment);
        
        // Watch Deployment 的创建
        Watch watch = client.apps().deployments().inNamespace(NAMESPACE).watch(new Watcher<Deployment>() {
			@Override
			public void eventReceived(Action action, Deployment resource) {
				log.info("=========> {}", action);
			}

			@Override
			public void onClose(KubernetesClientException cause) {
				log.error("Error {}", cause.getMessage(), cause);
			} 
        	
        });
        
        try {
			TimeUnit.SECONDS.sleep(20L);
			watch.close();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 扩容副本数
	 */
	@Test
	public void scaleDeployment() {
        client.apps().deployments().inNamespace(NAMESPACE).withName("psp-tomcat").scale(2, true);
	}
	
	/**
	 * 查找并删除Deployment
	 */
	@Test
	public void findAnddeleteDeployment() {
		Deployment deployment = client.apps().deployments().inNamespace(NAMESPACE).withName("psp-tomcat").get();
		if(deployment != null) {
			client.resource(deployment).delete();
		}
	}

}
