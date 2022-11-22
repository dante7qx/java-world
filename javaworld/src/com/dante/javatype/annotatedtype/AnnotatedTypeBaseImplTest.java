package com.dante.javatype.annotatedtype;

import java.util.Arrays;
import java.util.List;

public class AnnotatedTypeBaseImplTest<T> {

    private String a;

    private List<String> b;

    private T c;

    private String[] d;
    
    private T[] e;

    public static void main(String[] args) {
        Arrays.stream(AnnotatedTypeBaseImplTest.class.getDeclaredFields())
                .forEach(field -> System.out.println(field.getAnnotatedType().getClass()));
    }
}