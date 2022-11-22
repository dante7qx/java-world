package com.dante.javatype.annotatedtype;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.AnnotatedArrayType;
import java.lang.reflect.AnnotatedParameterizedType;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.AnnotatedTypeVariable;
import java.util.Arrays;
import java.util.List;

/**
 * AnnotatedArrayType是指那些可能带注解的数组类型的数据，当然啦其内部元素类型本身也可能是带注解的类型。
 * 注意AnnotatedArrayType代表的是所有数组类型（包含普通类型数组和泛型数组），而不是单指Type的子接口GenericArrayType，GenericArrayType只代表那些泛型数组
 * 
 * @author dante
 *
 */
public class AnnotatedArrayTypeTest<T extends @MyAnnotation(8) Number> {

    public @MyAnnotation(1) String @MyAnnotation(2) [] array1;

    public @MyAnnotation(3) List<@MyAnnotation(4) String> @MyAnnotation(5) [] array2;

    public @MyAnnotation(6) T @MyAnnotation(7) [] array3;
	
    // 注意二维数组第一个[]代表的是二维数组本身，第二个[]代表的是二维数组的组件本身，所以@MyAnnotation(10)
    // 是二维数组本身上的注解，@MyAnnotation(11)是二维数组的组件本身上的注解，@MyAnnotation(9)是二维数组
    // 组件类型上的注解
    public @MyAnnotation(9) String @MyAnnotation(10) [] @MyAnnotation(11) [] array4;

    public static void main(String[] args) {
        Arrays.stream(AnnotatedArrayTypeTest.class.getDeclaredFields()).forEach(field -> {
            print((AnnotatedArrayType) field.getAnnotatedType());
        });
    }

    private static void print(AnnotatedArrayType annotatedArrayType) {
        AnnotatedType annotatedGenericComponentType = annotatedArrayType.getAnnotatedGenericComponentType();
        // 获取域上的注解，即[]上的注解
        System.out.println(Arrays.toString(annotatedArrayType.getDeclaredAnnotations()));
        // 获取组件上的注解，即数组元素类型上的注解
        System.out.println(Arrays.toString(annotatedGenericComponentType.getDeclaredAnnotations()));
        // 获取组件类型为AnnotatedParameterizedType的泛型参数上的注解
        if (annotatedGenericComponentType instanceof AnnotatedParameterizedType) {
            AnnotatedParameterizedType annotatedParameterizedType = (AnnotatedParameterizedType) annotatedGenericComponentType;
            System.out.println(Arrays.toString(annotatedParameterizedType.getAnnotatedActualTypeArguments()[0].getDeclaredAnnotations()));
            // 获取组件类型为AnnotatedTypeVariable的所有边界上的注解
        } else if (annotatedGenericComponentType instanceof AnnotatedTypeVariable) {
            AnnotatedTypeVariable annotatedTypeVariable = (AnnotatedTypeVariable) annotatedGenericComponentType;
            Arrays.stream(annotatedTypeVariable.getAnnotatedBounds()).forEach(annotatedType -> System.out.println(Arrays.toString(annotatedType.getDeclaredAnnotations())));
            // 递归获取组件类型为AnnotatedArrayType上的注解
        } else if (annotatedGenericComponentType instanceof AnnotatedArrayType) {
            print((AnnotatedArrayType) annotatedGenericComponentType);
        }
    }

    @Target({ElementType.FIELD, ElementType.TYPE_USE, ElementType.TYPE, ElementType.PARAMETER})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public @interface MyAnnotation {

        int value();
    }

}
