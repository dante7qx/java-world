package com.dante.jdk10.inc;

/**
 * 默认方法和静态方法可以共享接口中的私有方法。
 * 如果私有方法是静态的，那这个方法就属于这个接口的。并且没有静态的私有方法只能被在接口中的实例调用。
 * 
 * @author dante
 *
 */
public interface InterfaceWithPrivateMethod {
	
	// 私有接口方法
	private String privateMethod() {
		return "I am a interface private method.";
	}
	
	// 静态的私有接口方法
	private static String staticPrivate() {
        return "static private.";
    }
	
	default void defaultMethod() {
		String str = privateMethod() + " <===> " + staticPrivate();
		System.out.println(str);
	}
	
	void print();
	
}

class InterfaceWithPrivateMethodImpl implements InterfaceWithPrivateMethod {

	@Override
	public void print() {
		System.out.println("print......");
	}
	
}
