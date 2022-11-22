package com.dante.javatype.annotatedtype;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.AnnotatedParameterizedType;
import java.lang.reflect.AnnotatedWildcardType;
import java.util.Arrays;
import java.util.List;

/**
 * AnnotatedWildcardType是指那些可能带注解的WildcardType类型的数据，当然其上限或下限本身可能也是带注解的类型
 * 
 * @author dante
 *
 */
public class AnnotatedWildcardTypeTest {
	
	public List<? extends @MyAnnotation(1) Number> list1;

    public List<? super @MyAnnotation(2) Number> list2;

    public static void main(String[] args) {
        Arrays.stream(AnnotatedWildcardTypeTest.class.getDeclaredFields())
                .forEach(field -> {
                    AnnotatedParameterizedType annotatedParameterizedType = (AnnotatedParameterizedType) field.getAnnotatedType();
                    print((AnnotatedWildcardType) annotatedParameterizedType.getAnnotatedActualTypeArguments()[0]);
                });
    }

    private static void print(AnnotatedWildcardType annotatedWildcardType) {
        Arrays.stream(annotatedWildcardType.getAnnotatedUpperBounds()).forEach(annotatedType -> System.out.println(Arrays.toString(annotatedType.getDeclaredAnnotations())));
        Arrays.stream(annotatedWildcardType.getAnnotatedLowerBounds()).forEach(annotatedType -> System.out.println(Arrays.toString(annotatedType.getDeclaredAnnotations())));
    }

    @Target({ElementType.FIELD, ElementType.TYPE_USE, ElementType.TYPE, ElementType.PARAMETER})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public @interface MyAnnotation {

        int value();
    }
	
}
