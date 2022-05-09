package com.dante.jdk8.optional;

/**
 * 根据Oracle文档，Optional是一个容器对象，可以包含也可以不包含非null值。Optional在Java 8中引入，目的是解决  NullPointerExceptions的问题。
 * 本质上，Optional是一个包装器类，其中包含对其他对象的引用。在这种情况下，对象只是指向内存位置的指针，并且也可以指向任何内容。
 * 从其它角度看，Optional提供一种类型级解决方案来表示可选值而不是空引用。
 * 
 * 参考：https://www.jdon.com/idea/java/using-optional-effectively-in-java-8.html

 * 
 * @author dante
 *
 */
public class Test {
	
	public static void main(String[] args) {
		String isoCode = getIsoCode(new User("但丁", 35, null));
		System.out.println(isoCode);
	}
	
	// Optional 之前的判断
	private static String getIsoCode(User user){
		String isoCode = null;
        if (user != null) {
        	Address address = user.getAddress();
            if (address != null) {
                Country country = address.getCountry();
                if (country != null) {
                	isoCode = country.getIsoCode();
                    if (isoCode != null) {
                    	isoCode = isoCode.toUpperCase();
                    }
                }
            }
        }
        return isoCode;
    }


}
