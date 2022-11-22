package com.dante.javatype.type;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.WildcardType;
import java.util.Arrays;
import java.util.List;

/**
 * WildcardType表示通配符类型表达式，例如?、? extends Number、? super Integer
 * 
 * @author dante
 *
 * @param <T>
 */
public class WildcardTypeTest<T> {

	public List<? extends Number> list1;

    public List<? super Number> list2;

    public List<? extends T> list3;

    public static void main(String[] args) throws Exception {
        ParameterizedType list1 = (ParameterizedType) WildcardTypeTest.class.getField("list1").getGenericType();
        ParameterizedType list2 = (ParameterizedType) WildcardTypeTest.class.getField("list2").getGenericType();
        ParameterizedType list3 = (ParameterizedType) WildcardTypeTest.class.getField("list3").getGenericType();
        print((WildcardType) list1.getActualTypeArguments()[0]);
        print((WildcardType) list2.getActualTypeArguments()[0]);
        print((WildcardType) list3.getActualTypeArguments()[0]);
        
        // main方法中通过反射将WildcardTypeTest类中的三个ParameterizedType的集合中的WildcardType的类型参数的上边界和下边界分别打印出来了
    }
    
    private static void print(WildcardType wildcardType) {
        System.out.println("UpperBounds："
                + Arrays.toString(wildcardType.getUpperBounds())
                + ","
                + "LowerBounds："
                + Arrays.toString(wildcardType.getLowerBounds()));
    }
	
}
