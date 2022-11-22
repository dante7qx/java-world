package com.dante.javatype.type;

import java.io.Serializable;
import java.lang.reflect.TypeVariable;
import java.util.Arrays;

/**
 * TypeVariable意为类型变量，也就是ParameterizedType中的变量，也就是我们常见的T、O这些泛型参数，
 * 例如List<String>中的String就是TypeVariable，而整个List<String>则代表着ParameterizedType。
 * 
 * @author dante
 *
 * @param <T>
 */
public class TypeVariableTest<T extends Number & Cloneable & Serializable> {
	
	<O> TypeVariableTest(O o) {}

    private static <T> void test(T t) {}


    public static void main(String[] args) throws Exception {
        // 获取类上声明的类型变量
        print(TypeVariableTest.class.getTypeParameters()[0]);

        // 获取构造器定义的类型变量
        print(TypeVariableTest.class.getDeclaredConstructors()[0].getTypeParameters()[0]);

        // 获取方法定义的类型变量
        print(TypeVariableTest.class.getDeclaredMethod("test", Object.class).getTypeParameters()[0]);

    }

    private static void print(TypeVariable typeVariable) {
        System.out.println(typeVariable.getGenericDeclaration()
                + "["
                + typeVariable.getName()
                + ","
                + Arrays.toString(typeVariable.getBounds())
                + "]"
        );
    }
	
}
