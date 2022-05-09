package com.dante.jdk14;

public class FeatureTest {

	public static void main(String[] args) {
		Object o = new Person("Tom", 18);

		if (o instanceof Person p) {
			System.out.println(p);
		}
		
		FeatureTest future = new FeatureTest();
		System.out.println(future.test(new Person("Tom", 18)));

	}

	public boolean test(Person person) {
		Object obj = new Person("Tom", 18);
		return obj instanceof Person p && person.equals(obj);
	}

	/**
	 * 1、record只能作用于类，且该类为final修饰，不能被继承。属性都是final修饰的，不能再被赋值，不能完全代替JavaBeans使用。
	 * 2、继承了java.lang.Record，不可以在继承其它类。
	 * 3、可以定义静态属性、静态或实例方法、构造函数
	 * 
	 * @author dante
	 *
	 */
	public record Person(String name, int age) {
		public static String address;

		public String getName() {
			return name;
		}
	}
}
