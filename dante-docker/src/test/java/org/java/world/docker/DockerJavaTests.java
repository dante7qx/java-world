package org.java.world.docker;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.AuthConfig;
import com.github.dockerjava.api.model.Image;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.command.PullImageResultCallback;
import com.github.dockerjava.core.command.PushImageResultCallback;

import lombok.extern.slf4j.Slf4j;

/**
 * 参考：https://www.baeldung.com/docker-java-api
 * 
 * @author dante
 *
 */
@Slf4j
public class DockerJavaTests {
	
	private DockerClient dockerClient;
	private AuthConfig authConfig;
	
	private static final String REGISTRY = "http://harbor.xiaohehe.com";
	private static final String SERVER_URL = "tcp://harbor.xiaohehe.com:4789";
	private static final String HARBOR_USER = "admin";
	private static final String HARBOR_PWD = "Caas12345_";
	private static final String SOURCE_IMAGE = "harbor.xiaohehe.com/spiritdev/busy-wow";
	private static final String SOURCE_IMAGE_TAG = "v2";
	private static final String TARGET_IMAGE = "harbor.xiaohehe.com/spirituat/busy-wow";
	private static final String TARGET_IMAGE_TAG = "v2";
	
	
	@Before
	public void init() {
		authConfig = new AuthConfig();
		authConfig.withRegistryAddress(REGISTRY);
		authConfig.withUsername(HARBOR_USER);
		authConfig.withPassword(HARBOR_PWD);
		dockerClient = DockerClientBuilder.getInstance(SERVER_URL).build();
	}
	
	@After
	public void close() {
		try {
			if(dockerClient != null) {
				dockerClient.close();
			}
		} catch (IOException e) {
			log.error("Close DockerClient error.", e);
		}
	}

	@Test
	public void listImage() {
		try {
			List<Image> images = dockerClient.listImagesCmd().exec();
			log.info("Images -> {}", images);
		} catch (Exception e) {
			log.error("listImage ==============> ", e);
		}
	}
	
	@Test 
	public void pullImage() {
		boolean pullResult = false;
		try {
			pullResult = dockerClient.pullImageCmd(SOURCE_IMAGE)
			  .withTag(SOURCE_IMAGE_TAG)
			  .withAuthConfig(authConfig)
			  .exec(new PullImageResultCallback())
			  .awaitCompletion(3, TimeUnit.MINUTES);
			log.info("Pull Image result is {}", pullResult);
		} catch (InterruptedException e) {
			log.error("pullImage ==============> ", e);
		}
	}
	
	@Test
	public void tagImage() {
		List<Image> images = dockerClient.listImagesCmd()
			.withImageNameFilter(SOURCE_IMAGE.concat(":").concat(SOURCE_IMAGE_TAG))
			.exec();
		if(images != null && !images.isEmpty()) {
			String imageId = images.get(0).getId();
			dockerClient.tagImageCmd(imageId, TARGET_IMAGE, TARGET_IMAGE_TAG).exec();
		}
	}
	
	@Test
	public void pushImage() {
		try {
			dockerClient.pushImageCmd(TARGET_IMAGE)
			  .withTag(TARGET_IMAGE_TAG)
			  .withAuthConfig(authConfig)
			  .exec(new PushImageResultCallback())
			  .awaitCompletion(90, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			log.error("pushImage ==============> ", e);
		}
	}
	
	@Test
	public void removeImage() {
		List<Image> images = dockerClient.listImagesCmd()
				.withImageNameFilter(SOURCE_IMAGE.concat(":").concat(SOURCE_IMAGE_TAG))
				.exec();
		String imageId = images.get(0).getId();
		dockerClient.removeImageCmd(imageId)
			.withForce(true)
			.exec();
	}
	
	@Test
	public void pipeline() {
		boolean pullResult = false;
		String imageId = "";
		try {
			// 1. 拉取镜像
			pullResult = dockerClient.pullImageCmd(SOURCE_IMAGE)
			  .withTag(SOURCE_IMAGE_TAG)
			  .withAuthConfig(authConfig)
			  .exec(new PullImageResultCallback())
			  .awaitCompletion(3, TimeUnit.MINUTES);
			log.info("Pull Image result is {}", pullResult);
			if(pullResult) {
				// 2. Tag 镜像
				List<Image> images = dockerClient.listImagesCmd()
						.withImageNameFilter(SOURCE_IMAGE.concat(":").concat(SOURCE_IMAGE_TAG))
						.exec();
				if(images != null && !images.isEmpty()) {
					imageId = images.get(0).getId();
					dockerClient.tagImageCmd(imageId, TARGET_IMAGE, TARGET_IMAGE_TAG).exec();
					
					// 3. 推送镜像
					try {
						boolean pushResult = dockerClient.pushImageCmd(TARGET_IMAGE)
						  .withTag(TARGET_IMAGE_TAG)
						  .withAuthConfig(authConfig)
						  .exec(new PushImageResultCallback())
						  .awaitCompletion(3, TimeUnit.MINUTES);
						log.info("Push Image result is {}", pushResult);
					} catch (InterruptedException e) {
						log.error("pushImage ==============> ", e);
					}
					
					// 4. 删除本地镜像
					dockerClient.removeImageCmd(imageId).withForce(true).exec();
				}
			}
		} catch (InterruptedException e) {
			log.error("pullImage ==============> ", e);
		}
	}
	
}
