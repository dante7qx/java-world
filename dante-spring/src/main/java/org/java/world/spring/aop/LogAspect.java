package org.java.world.spring.aop;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect			// 声明一个切面
@Component
public class LogAspect {
	/**
	 * 声明切点
	 */
	@Pointcut("@annotation(org.java.world.spring.aop.Actional)")
	public void annotationPointCut() {}
	
	@After("annotationPointCut()")
	public void after(JoinPoint joinPoint) {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method m = signature.getMethod();
		Actional actional = m.getAnnotation(Actional.class);
		System.out.println("注解式拦截： " + actional.name());
	}
	
	@Before("execution(* org.java.world.spring.aop.DemoMethodService.*(..))")
	public void before(JoinPoint joinPoint) {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method m = signature.getMethod();
		System.out.println("方法规则拦截： " + m.getName());
		
	}
	
}
