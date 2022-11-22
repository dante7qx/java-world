package com.dante.javatype.type;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * 利用ParameterizedType的getActualTypeArguments方法定义一个TypeToken，通过new一个匿名的子类然后反射获取被擦出对象的实际类型。
 * 
 * @author dante
 *
 * @param <T>
 */
public abstract class TypeToken<T> {
	
	private final Type runtimeType;
	
	public TypeToken() {
		// 返回表示此 Class 对象表示的实体（类、接口、原始类型或 void）的直接超类的 Type。
        Type superclass = getClass().getGenericSuperclass();
        if (!(superclass instanceof ParameterizedType)) {
            throw new IllegalArgumentException(getClass() + "必须是参数化类型");
        }
        runtimeType = ((ParameterizedType) superclass).getActualTypeArguments()[0];
        System.out.println(runtimeType.getTypeName());
    }
	
	public static void main(String[] args) {
		new TypeToken<List<String>>() {};
	}
}
