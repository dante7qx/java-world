package com.dante.javatype.type;

import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.List;

public class ParameterizedTypeTest<T> {

	public static void main(String[] args) {
		class F<T> {
		}
		class E extends F<String> {

		}
		print(ParameterizedTypeTest.B.class);
		print(ParameterizedTypeTest.C.class);
		print(D.class);
		print(E.class);
	}

	private static void print(Class<?> clazz) {
		ParameterizedType type = (ParameterizedType) clazz.getGenericSuperclass();
		String str = clazz.getName() + "[" + Arrays.toString(type.getActualTypeArguments()) + " -> " + type.getRawType()
				+ " -> " + type.getOwnerType() + "]";
		System.out.println(str);

	}

	static class A<T> {

	}

	static class B<T> extends ParameterizedTypeTest.A<T> {

	}

	static class C extends ParameterizedTypeTest.A<List<String>> {

	}

}

class D extends ParameterizedTypeTest<String> {
}