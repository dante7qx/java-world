package org.java.world.spring.spel;


import java.io.File;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

@Configuration
@ComponentScan("org.java.world.spring.spel")
@PropertySource({"classpath:org/java/world/spring/spel/test.properties", "classpath:config/dev.properties"})
public class ElConfig {
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigure() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Value("normal->普通字符")
	private String normal;

	@Value("#{systemProperties['os.name']}")
	private String osName;
	
	@Value("#{T (java.lang.Math).random() * 100.0}")
	private double randomNumber;
	
	@Value("#{elService.another}")
	private String fromAnother;
	
	@Value("${db.password}")
	private String dbPassword;
	
	@Value("classpath:spel.data")
	private Resource testFile;
	
	@Value("/Users/dante/Documents/Project/dante-spring/src/main/resources/spel")
	private FileSystemResource testDirectory;
	
	@Value("http://www.baidu.com")
	private Resource siteUrl;
	
	@Value("${profile}")
	private String profile;
	
	@Autowired
	private Environment environment;

	public void outputResource() throws IOException {
		System.out.println(normal);
		System.out.println(osName);
		System.out.println(randomNumber);
		System.out.println(fromAnother);
		System.out.println(dbPassword);
		System.out.println(environment.getProperty("db.username"));
		System.out.println("profile -> " + profile);
		System.out.println(IOUtils.toString(testFile.getInputStream()));
		File dir = testDirectory.getFile();
		if(dir.isDirectory()) {
			File[] files = dir.listFiles();
			for (File file : files) {
				System.out.println(file.getName());
			}
		}
		System.out.println(IOUtils.toString(siteUrl.getInputStream()));
	}
	
}
