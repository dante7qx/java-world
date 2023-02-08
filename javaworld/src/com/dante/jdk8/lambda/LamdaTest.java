package com.dante.jdk8.lambda;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.stream.IntStream;

/**
 * 函数式接口只能有一个方法（除了default）
 * 
 * @author dante
 */
@FunctionalInterface
interface LamdaInc {
	default void print(String str) {
		System.out.println(str);
	};

	public int add(int x, int y);
}

interface LamdaInc2 {
	public double mul(double x, double y);
}

public class LamdaTest {

	public static void main(String[] args) {
		LamdaInc inc = (x, y) -> x + y;
		inc.print("结算结果：");
		System.out.println(inc.add(10, 20));

		LamdaInc2 inc2 = (x, y) -> {
			return x * y;
		};
		System.out.println(inc2.mul(23, 3.6));

		System.out.println("******************************************");
		IntStream.range(1, 3)
			.forEach(System.out::println);

		System.out.println("******************************************");
		List<Integer> list = Arrays.asList(32, 43, 54, 12, 87, 198, 49);
		list.forEach(e -> System.out.println(e));
		System.out.println("******************************************");

		// Lambda仅有一个类型需要推断的参数时，参数名称两边的括号也可以省略
		System.out.println(modify(10, i -> "你好，" + i * i));

		// Lambda可以没有限制地捕获(也就是在其主体中引用)实例变量和静态变量，但局部变量必须显式声明为final。
		final int x = 25;
		System.out.println(oper(10, 20, (i, j) -> i * x + j * 10));

	}

	static String modify(Integer i, Function<Integer, String> func) {
		return func.apply(i);
	}

	static int oper(int i, int j, IntBinaryOperator oper) {
		return oper.applyAsInt(i, j);
	}

}
