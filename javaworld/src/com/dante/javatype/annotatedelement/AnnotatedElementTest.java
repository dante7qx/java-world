package com.dante.javatype.annotatedelement;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Arrays;

public class AnnotatedElementTest {

	public static void main(String[] args) {
        // A类上是否“存在”MyAnnotation注解
        System.out.println(A.class.isAnnotationPresent(MyAnnotation.class));

        // A类上是否“存在”RepeatableAnnotation注解
        System.out.println(A.class.isAnnotationPresent(RepeatableAnnotation.class));

        // 如果A类上“存在”MyAnnotation注解，则返回该注解，否则返回null
        System.out.println(A.class.getAnnotation(MyAnnotation.class));

        // 如果A类上“存在”RepeatableAnnotation注解，则返回该注解，否则返回null
        System.out.println(A.class.getAnnotation(RepeatableAnnotation.class));

        // 获取A类上所有“存在”的注解
        System.out.println(Arrays.toString(A.class.getAnnotations()));

        // 如果指定的注解是与A类“关联的”，则返回该注解，否则再去它的父类中取寻找。
        // 如果参数注解是可重复注解，则会从该元素上的容器注解中寻找该重复注解并返回。
        System.out.println(Arrays.toString(A.class.getAnnotationsByType(MyAnnotation.class)));
        System.out.println(Arrays.toString(A.class.getAnnotationsByType(RepeatableAnnotation.class)));

        // 获取“直接存在”与该元素上的所有注解
        System.out.println(Arrays.toString(A.class.getDeclaredAnnotations()));

        // 获取“直接存在”于该元素上的指定注解
        System.out.println(A.class.getDeclaredAnnotation(MyAnnotation.class));
        System.out.println(A.class.getDeclaredAnnotation(RepeatableAnnotation.class));

        // 获取“直接存在”或者“间接存在”于该元素上的注解
        System.out.println(Arrays.toString(A.class.getDeclaredAnnotationsByType(MyAnnotation.class)));
        System.out.println(Arrays.toString(A.class.getDeclaredAnnotationsByType(RepeatableAnnotation.class)));
    }

    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @Inherited
    @Documented
    public @interface RepeatableAnnotation {

        MyAnnotation[] value();
    }

    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @Repeatable(RepeatableAnnotation.class)
    @Inherited
    @Documented
    public @interface MyAnnotation {

        int value();
    }

    @MyAnnotation(1)
    @MyAnnotation(2)
    static class A extends B {

    }

    @MyAnnotation(3)
    @MyAnnotation(4)
    static class B {

    }

	
}
