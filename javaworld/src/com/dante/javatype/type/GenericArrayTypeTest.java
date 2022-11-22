package com.dante.javatype.type;

import java.lang.reflect.GenericArrayType;
import java.util.List;

/**
 * GenericArrayType表示那些组件类型为ParameterizedType或TypeVariable的数组。（泛型数组）
 * 
 * @author dante
 *
 * @param <T>
 */
public class GenericArrayTypeTest<T> {
	public T[] array1;

    public T[][] array2;

    public List<String>[] array3;

    public List<?>[] array4;
    
    public static void main(String[] args) throws Exception {
        GenericArrayType array1 = (GenericArrayType) GenericArrayTypeTest.class.getDeclaredField("array1").getGenericType();
        GenericArrayType array2 = (GenericArrayType) GenericArrayTypeTest.class.getDeclaredField("array2").getGenericType();
        GenericArrayType array3 = (GenericArrayType) GenericArrayTypeTest.class.getDeclaredField("array3").getGenericType();
        GenericArrayType array4 = (GenericArrayType) GenericArrayTypeTest.class.getDeclaredField("array4").getGenericType();
        print(array1);
        print(array2);
        print(array3);
        print(array4);
    }

    private static void print(GenericArrayType genericArrayType) {
        System.out.println(genericArrayType.getGenericComponentType().getClass());
    }
    
}
