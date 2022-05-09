package org.dante.springmvc.spring.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class RequestTimeInterceptor extends HandlerInterceptorAdapter {
	
	private final static Logger logger = LoggerFactory.getLogger(RequestTimeInterceptor.class);
	
	/**
	 * 在实际的handler被执行前被调用
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		long startTime = System.currentTimeMillis();
		request.setAttribute("startHttpRequestTime", startTime);
		return true;
	}

	/**
	 * 在handler被执行后被调用
	 */
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
//		logger.info("postHandle");
		long startTime = (long) request.getAttribute("startHttpRequestTime");
		request.removeAttribute("startHttpRequestTime");
		long endTime = System.currentTimeMillis();
		logger.info("[" + request.getRequestURI() + "] spend -> " + new Long(endTime - startTime) + "ms");
	}
	
	@Override
	public void afterConcurrentHandlingStarted(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
//		logger.info("afterConcurrentHandlingStarted");
	}
	
	/**
	 * 当request处理完成后被调用
	 */
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
//		logger.info("afterCompletion");
	}
	
}

