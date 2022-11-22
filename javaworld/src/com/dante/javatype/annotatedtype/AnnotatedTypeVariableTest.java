package com.dante.javatype.annotatedtype;

import java.io.Serializable;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.AnnotatedTypeVariable;
import java.util.Arrays;

/**
 * AnnotatedTypeVariable是指那些可能带注解的TypeVariable类型的数据，
 * 例如T t是一个TypeVariable类型的值，同时也是一个AnnotatedTypeVariable类型的值，
 * 只不过此时它本身没有带注解而已，当了啦类型变量本身是带边界的，
 * 所以它的所有边界都有可能是带注解的任何AnnotatedType，所以AnnotatedTypeVariable提供了getAnnotatedBounds方法来获取所有带注解的边界。
 * 
 * @author dante
 *
 * @param <T>
 */
public class AnnotatedTypeVariableTest<T extends @MyAnnotation(1) Number & @MyAnnotation(2) Cloneable & @MyAnnotation(3) Serializable> {

	public T t;

	public static void main(String[] args) throws Exception {
		AnnotatedTypeVariable annotatedTypeVariable = (AnnotatedTypeVariable) AnnotatedTypeVariableTest.class
				.getDeclaredField("t").getAnnotatedType();
		Arrays.stream(annotatedTypeVariable.getAnnotatedBounds())
				.forEach(annotatedType -> System.out.println(Arrays.toString(annotatedType.getDeclaredAnnotations())));
	}

	@Target({ElementType.FIELD, ElementType.TYPE_USE, ElementType.TYPE, ElementType.PARAMETER})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public @interface MyAnnotation {

        int value();
    }
}