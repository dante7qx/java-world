package org.java.world.spring.aware;

import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

/**
 * Spring Aware的目的是为了让Bean获得Spring容器的服务
 * 
 * @author dante
 *
 */
@Service
public class AwareService implements BeanNameAware, ResourceLoaderAware {

	private String beanName;
	private ResourceLoader loader;

	@Override
	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.loader = resourceLoader;
	}

	@Override
	public void setBeanName(String name) {
		this.beanName = name;
	}

	public void print() {
		System.out.println("Bean的名称是：" + beanName);

		Resource resource = loader
				.getResource("classpath:org/java/world/spring/aware/text.txt");
		System.out.println("test.txt的内容：");
		try {
			System.out.println(IOUtils.toString(resource.getInputStream(), Charset.forName("UTF-8")));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
