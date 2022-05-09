package com.dante.annotation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class FunctionalUtils {
	
	/**
	 * 处理方法的注解
	 * 
	 * @param clazz
	 */
	public static void handleMethod(Class<?> clazz) {
		try {
			Object obj = clazz.getConstructor(new Class[]{}).newInstance(new Object[]{});
			for(Method method : clazz.getDeclaredMethods()){
				if(method.isAnnotationPresent(Functional.class)) {
					Functional function = method.getAnnotation(Functional.class);
					if(function != null) {
						String[] functionCodeArr = function.value();
						String[] functionNameArr = function.name();
						System.out.println(Arrays.asList(functionNameArr));
						System.out.println(Arrays.asList(functionCodeArr));
						method.invoke(obj, method.getParameterTypes());
					}
				}
			}
		} catch (IllegalArgumentException | SecurityException | InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			System.out.println("记录日志：" + e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * 处理接口、类、枚举、注解的注解
	 * 
	 * @param clazz
	 */
	public static void handleType(Class<?> clazz) {
		Functional function = clazz.getAnnotation(Functional.class);
		if(function != null) {
			System.out.println("该类需要权限控制！");
		}
	}
	
	
}
