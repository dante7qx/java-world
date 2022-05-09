package org.dante.springmvc.spring.config;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import org.dante.springmvc.spring.converter.CustomMessageConverter;
import org.dante.springmvc.spring.interceptor.RequestTimeInterceptor;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
@EnableWebMvc
//@EnableScheduling
@Import({ PlaceholderConfig.class, MyBatisConfig.class, ShiroConfig.class })
public class MvcConfig extends WebMvcConfigurerAdapter {

	@Bean
	public InternalResourceViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/classes/views/");
		viewResolver.setSuffix(".jsp");
		viewResolver.setViewClass(JstlView.class);
		return viewResolver;
	}
	
	@Bean
	public CharacterEncodingFilter charsetFilter() {
		CharacterEncodingFilter charsetFilter = new CharacterEncodingFilter();
		charsetFilter.setEncoding("utf-8");
		charsetFilter.setForceEncoding(true);
		return charsetFilter;
	}
	
	/**
	 * 文件上传（单位：kb）
	 */
	@Bean
	public MultipartResolver multipartResolver() {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setMaxUploadSizePerFile(2097152); // 2M
		multipartResolver.setMaxUploadSize(20971520); // 20M
		multipartResolver.setMaxInMemorySize(409600);
		return multipartResolver;
	}

	@Bean
	public RequestTimeInterceptor requestTimeInterceptor() {
		return new RequestTimeInterceptor();
	}

	@Bean
	public StringHttpMessageConverter stringHttpMessageConverter() {
		StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter();
		stringHttpMessageConverter.setDefaultCharset(Charset.forName("UTF-8"));
		stringHttpMessageConverter
				.setSupportedMediaTypes(Arrays.asList(new MediaType("text", "plain", Charset.forName("UTF-8"))));
		return stringHttpMessageConverter;
	}

	@Bean
	public CustomMessageConverter customMessageConverter() {
		return new CustomMessageConverter();
	}

	/**
	 * 配置页面跳转
	 */
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("index");
		registry.addViewController("/register").setViewName("register");
		registry.addViewController("/loginUrl").setViewName("login");
		registry.addViewController("/unauthorizedUrl").setViewName("unauthorizedUrl");
		registry.addViewController("/uploadfile").setViewName("upload/upload");
		registry.addViewController("/converter").setViewName("converter/converter");
		registry.addViewController("/sse").setViewName("serverpush/sse");
		registry.addViewController("/async").setViewName("serverpush/async");
		registry.addViewController("/permission").setViewName("admin/permission");
		registry.addViewController("/role").setViewName("admin/role");
		registry.addViewController("/menu").setViewName("admin/menu");
		registry.addViewController("/sysuser").setViewName("admin/user");
	}

	/**
	 * 静态资源映射
	 * 
	 * 1、handler: 对外暴露的访问路径 2、location: 文件放置的目录
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/assets/**").addResourceLocations("classpath:/assets/");
	}

	/**
	 * 注册拦截器
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(requestTimeInterceptor());
	}

	/**
	 * 路径参数配置
	 */
	@Override
	public void configurePathMatch(PathMatchConfigurer configurer) {
		// 设置是否是后缀模式匹配，如“/user”是否匹配"/user.*"，默认真即匹配；
		configurer.setUseSuffixPatternMatch(false);
	}

	/**
	 * 数据转换器配置
	 */
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		super.configureMessageConverters(converters);
		// converters.add(stringHttpMessageConverter());
	}

	/**
	 * 添加自定义HttpMessageConverter
	 */
	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(customMessageConverter());
	}

	/**
	 * DAO接口所在包名，Spring会自动查找其下的类 
	 * 
	 * @return
	 */
	@Bean
	public MapperScannerConfigurer mapperScannerConfigurer() {
		MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
		mapperScannerConfigurer.setBasePackage("org.dante.springmvc.mybatis.dao");
		mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
		return mapperScannerConfigurer;
	}
	
}
