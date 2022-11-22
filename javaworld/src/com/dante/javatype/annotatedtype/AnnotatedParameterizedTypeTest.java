package com.dante.javatype.annotatedtype;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.AnnotatedParameterizedType;
import java.util.Arrays;
import java.util.List;

/**
 * AnnotatedParameterizedType是指那些可能带注解的ParameterizedType类型的数据，
 * 例如List<String> list是一个ParameterizedType，同时也是一个AnnotatedParameterizedType，
 * 只不过此时它本身没有带注解而已，当然啦，它的类型参数<String>也是有可能带注解的任何AnnotatedType，
 * 所以AnnotatedParameterizedType提供了getAnnotatedActualTypeArguments方法来获取带注解的类型参数。
 * 
 * @author dante
 *
 */
public class AnnotatedParameterizedTypeTest {
	
	public List<@MyAnnotation(1) String> list1;

    public List<@MyAnnotation(2) List<@MyAnnotation(3) String>> list2;

    public static void main(String[] args) {
        Arrays.stream(AnnotatedParameterizedTypeTest.class.getDeclaredFields()).forEach(field -> print((AnnotatedParameterizedType) field.getAnnotatedType()));
    }

    private static void print(AnnotatedParameterizedType annotatedParameterizedType) {
        Arrays.stream(annotatedParameterizedType.getAnnotatedActualTypeArguments())
                .forEach(annotatedType -> {
                            System.out.println(Arrays.toString(annotatedType.getDeclaredAnnotations()));
                            if (annotatedType instanceof AnnotatedParameterizedType) {
                                print((AnnotatedParameterizedType) annotatedType);
                            }
                        }
                );
    }

    @Target({ElementType.FIELD, ElementType.TYPE_USE, ElementType.TYPE, ElementType.PARAMETER})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public @interface MyAnnotation {

        int value();
    }
	
}
