package com.dante.syntax.inc;

/**
 * java规范里，方法名+参数（不含返回值类型）唯一确定一个方法。
 * 
 * 多个接口中有重名方法怎么办？
 * 实现类里写个内部类，分别实现两个接口
 * 
 * @author dante
 *
 */
public class IncTest  {
		
	/**
	 * 静态内部类（static inner class）
	 * 
	 * 1. 不包含外部类的this指针，并且在外部类装载时初始化
	 * 2. 静态内部类只能访问外部类static成员
	 * 3. 外部类访问静态内部类的成员
	 * 
	 * @author dante
	 *
	 */
	static class Painter implements IPainter {
		@Override
		public String draw() {
			System.out.println("我是个画家！");
			return "Painter";
		}
	}
	
	/**
	 * 成员内部类（nested inner class）
	 * 
	 * 1. 隐含有一个外部类的指针this, 可以访问外部类的一切资源
	 * 2. 外部类访问内部类的成员，先要取得内部类的对象,并且取决于内部类成员的封装等级
	 * 3. 不能包含任何static成员
	 * 
	 * @author dante
	 *
	 */
	class CowBoy implements ICowBoy {
		@Override
		public int draw() {
			System.out.println("我是个牛仔！");
			return 100;
		}
	}
	
	public static void main(String[] args) {
		// 静态内部类 new 外部类.静态内部类()
		IPainter painter = new IncTest.Painter();
		painter.draw();
		
		// 成员内部类 new 外部类().new 成员内部类()
		IncTest incTest = new IncTest();
		ICowBoy cowBoy = incTest.new CowBoy();
		cowBoy.draw();
	}


}
