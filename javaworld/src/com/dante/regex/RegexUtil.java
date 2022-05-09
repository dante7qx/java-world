package com.dante.regex;

/**
 * 参考资料：
 * 
 * http://www.cnblogs.com/lzq198754/p/5780340.html
 * 
 * @author dante
 *
 */
public class RegexUtil {
	
	public static void main(String[] args) {
		String x = "2321.js";
		
		if(x.matches(".*(?<!.gif|.jpg|.png|.bmp|.jpeg|.tiff|.js|.css)$")) {
			System.out.println(111);
		} else {
			System.out.println(333);
		}
	
		String y = "asdsa  AND ";
		if(y.toLowerCase().matches("^.*\\S*and\\S*.*$")) {
			System.out.println(111);
		} else {
			System.out.println(222);
		}
		
		String z = "131231\'11";
		if(z.toLowerCase().matches("^.*\\\\S?(\\\\')\\\\S?.*$")) {
			System.out.println(111);
		} else {
			System.out.println(222);
		}
		
		String aa = "LS/job/HiApp/job/FSS/job/SIT/ass-sa";
		if(aa.matches("^LS.*(HiApp).*(SIT).*")) {
			System.out.println("aaa");
		} else {
			System.out.println("bbb");
		}
	}
}
