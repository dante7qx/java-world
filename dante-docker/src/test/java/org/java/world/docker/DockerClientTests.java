package org.java.world.docker;

import java.nio.charset.Charset;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.DockerClient.ListContainersParam;
import com.spotify.docker.client.DockerClient.ListImagesFilterParam;
import com.spotify.docker.client.DockerClient.ListImagesParam;
import com.spotify.docker.client.DockerClient.LogsParam;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.Container;
import com.spotify.docker.client.messages.Image;
import com.spotify.docker.client.messages.RemovedImage;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DockerClientTests {
	
	private DockerClient dockerClient;
	
	@Before
	public void init() {
		dockerClient = new DefaultDockerClient("http://localhost:2376");
	}
	
	@After
	public void close() {
		dockerClient.close();
	}

	@Test
	public void image() {
		try {
			List<Image> images = dockerClient.listImages(ListImagesParam.allImages());
			images.forEach(i -> log.info("image -> {}", i));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void pullImage() {
		try {
			dockerClient.pull("busybox:latest");
		} catch (DockerException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void deleteImage() {
		try {
			List<Image> images = dockerClient.listImages(ListImagesFilterParam.byName("busybox:latest"));
			if(images != null && !images.isEmpty()) {
				String imageId = images.get(0).id();
				List<RemovedImage> removeImage = dockerClient.removeImage(imageId);
				log.info("RemovedImage => {}", removeImage);
			}
		} catch (DockerException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void container() {
		try {
			Charset charset = Charset.forName("utf-8");
			List<Container> containers = dockerClient.listContainers(ListContainersParam.withStatusExited());
			containers.forEach(c -> log.info("Container {}", c));
			List<String> mongoContainerIds = containers
				.stream()
				.filter(c -> "mongo".equals(c.image()))
				.map(c -> c.id())
				.collect(Collectors.toList());
			mongoContainerIds.forEach(cid -> {
				log.info("ContainerId: {}", cid);
				try {
					dockerClient.startContainer(cid);
					dockerClient.logs(cid, LogsParam.follow())
						.forEachRemaining(l -> log.info(charset.decode(l.content()).toString()));
					TimeUnit.SECONDS.sleep(60);
					dockerClient.stopContainer(cid, 10);
				} catch (DockerException e) {
					log.error("DockerException occur.", e);
				} catch (InterruptedException e) {
					log.error("InterruptedException occur.", e);
				}
			});
		} catch (Exception e) {
			log.error("Container error.", e);
		}
	}
}
