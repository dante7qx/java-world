package com.dante.jdk17;

import java.util.List;
import java.util.stream.Stream;

import jdk.incubator.vector.FloatVector;
import jdk.incubator.vector.VectorSpecies;

/**
 * 参考文档：
 * 
 * https://juejin.cn/post/7019952895999246366
 * https://github.com/eugenp/tutorials/tree/master/core-java-modules
 * 
 * @author dante
 *
 */
public class Feature17 {
	private static final VectorSpecies<Float> SPECIES = FloatVector.SPECIES_PREFERRED;

	public static void main(String[] args) {
		// 文本块
		textBlock();
		
		// switch表达式
		withSwitchExpression(Fruit.APPLE);
		withReturnValue(Fruit.MANGO);
		withYield(Fruit.PEAR);
		oldStyleWithYield(Fruit.AVOCADO);
		
		// record关键字
		testPerson();
		
		// instanceof模式匹配
		instanceofStyle(Fruit.APPLE);
		
		// Stream.toList()
		streamToList();
		
	}

	/**
	 * 文本块
	 */
	private static void textBlock() {
		String text = """
			{
			  "name": "小黑说Java",
			  "age": 18,
			  "address": "北京市西城区"
			}
			""";
		System.out.println(text);
	}

	/**
	 * switch表达式
	 * 
	 * 可以通过switch表达式来进行简化。将冒号（:）替换为箭头（->），并且switch表达式默认不会失败，所以不需要break
	 * 
	 */
	private static void withSwitchExpression(Fruit fruit) {
		switch (fruit) {
			case APPLE, PEAR -> System.out.println("普通水果");
			case MANGO, AVOCADO -> System.out.println("进口水果");
			default -> System.out.println("未知水果");
		}
	}
	
	/**
	 * switch表达式也可以返回一个值，比如上面的例子我们可以让switch返回一个字符串来表示我们要打印的文本。
	 * 需要注意在switch语句的最后要加一个分号
	 * 
	 * @param fruit
	 */
	private static void withReturnValue(Fruit fruit) {
	    String text = switch (fruit) {
	        case APPLE, PEAR -> "普通水果";
	        case MANGO, AVOCADO -> "进口水果";
	        default -> "未知水果";
	    };
	    System.out.println(text);
	}
	
	/**
	 * 如果你想在case里想做不止一件事，比如在返回之前先进行一些计算或者打印操作，可以通过大括号来作为case块，最后的返回值使用关键字yield进行返回。
	 * 
	 * @param fruit
	 */
	private static void withYield(Fruit fruit) {
	    String text = switch (fruit) {
	        case APPLE, PEAR -> {
	            System.out.println("给的水果是: " + fruit);
	            yield "普通水果";
	        }
	        case MANGO, AVOCADO -> "进口水果";
	        default -> "未知水果";
	    };
	    System.out.println(text);
	}

	/**
	 * 当然也可以直接使用yield返回结果
	 * 
	 * @param fruit
	 */
	private static void oldStyleWithYield(Fruit fruit) {
	    System.out.println(switch (fruit) {
	        case APPLE, PEAR:
	            yield "普通水果";
	        case MANGO, AVOCADO:
	            yield "进口水果";
	        default:
	            yield "未知水果";
	    });
	}

	/**
	 * record用于创建不可变的数据类。
	 */
	private static void testPerson() {
	    record PersonRecord(String name, int age) {
	        // 构造方法
	        PersonRecord {
	            System.out.println("name " + name + " age " + age);
	            if (name == null) {
	                throw new IllegalArgumentException("姓名不能为空");
	            }
	        }
	    }
	    new PersonRecord("但丁", 37);
	    new PersonRecord("潇潇", 33);
	}
	
	/**
	 * 可以将类型转换和变量声明都在if中处理。同时，可以直接在if中使用这个变量。
	 * 因为只有当instanceof的结果为true时，才会定义变量fruit，所以这里可以使用&&，但是改为||就会编译报错
	 * 
	 * @param o
	 */
	private static void instanceofStyle(Object o) {
		if (o instanceof Fruit fruit && fruit.getColor()== 1) {
	        System.out.println("This fruit is :" + fruit);
	    }

	}

	/**
	 * Stream.toList()
	 * 
	 * 在Java 17中将会变得简单，可以直接调用toList()。
	 * 
	 */
	private static void streamToList() {
	    Stream<String> stringStream = Stream.of("Hello", "World", "Java");
	    List<String> stringList =  stringStream.toList();
	    for(String s : stringList) {
	        System.out.println(s);
	    }
	}
	
	/**
	 * 引入一个 API 来表达向量计算，该计算可以在运行时可靠地编译为支持的 CPU 架构上的最佳向量指令，从而实现优于等效标量计算的性能
	 * 
	 * @param a
	 * @param b
	 * @param c
	 */
	public void newVectorComputation(float[] a, float[] b, float[] c) {
	    for (var i = 0; i < a.length; i += SPECIES.length()) {
	        var m = SPECIES.indexInRange(i, a.length);
	        var va = FloatVector.fromArray(SPECIES, a, i, m);
	        var vb = FloatVector.fromArray(SPECIES, b, i, m);
	        var vc = va.mul(vb);
	        vc.intoArray(c, i, m);
	    }
	}

	public void commonVectorComputation(float[] a, float[] b, float[] c) {
	    for (var i = 0; i < a.length; i ++) {
	        c[i] = a[i] * b[i];
	    }
	}
	
}
