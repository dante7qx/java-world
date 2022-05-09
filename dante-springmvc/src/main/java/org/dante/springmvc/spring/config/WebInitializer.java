package org.dante.springmvc.spring.config;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * WebApplicationInitializer 配置Servlet3.0，替代web.xml。
 * 实现此接口，将会自动被SpringServletContainerInitializer获取
 * 
 * @author dante
 */
public class WebInitializer implements WebApplicationInitializer {

	public void onStartup(ServletContext servletContext) throws ServletException {
		// 新建WebApplicationContext，注册配置类，和当前servletContext关联
		AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
		ctx.register(MvcConfig.class);

		// 添加Character Filter
		DelegatingFilterProxy characterFilter = new DelegatingFilterProxy();
		characterFilter.setBeanName("charsetFilter");
		FilterRegistration.Dynamic characterFilterRegistration = servletContext.addFilter("charsetFilter",
				characterFilter);
		characterFilterRegistration.addMappingForUrlPatterns(
				EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.INCLUDE), false, "/*");

		// 添加Shiro Filter
		DelegatingFilterProxy shiroFilter = new DelegatingFilterProxy();
		FilterRegistration.Dynamic shiroFilterRegistration = servletContext.addFilter("shiroFilter", shiroFilter);
		shiroFilterRegistration.setInitParameter("targetFilterLifecycle", "true");
		shiroFilterRegistration.addMappingForUrlPatterns(
				EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.INCLUDE), false, "/*");

		ctx.setServletContext(servletContext);

		// 注册DispatcherServlet
		Dynamic servlet = servletContext.addServlet("dispather", new DispatcherServlet(ctx));
		servlet.addMapping("/");
		servlet.setAsyncSupported(true); // 开启异步方法支持
		servlet.setLoadOnStartup(1);

	}

}
