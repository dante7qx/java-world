package basic

class HelloWorld {

	static void main(String[] args) {
		helloWorld();
		
//		sum(10);
//		sum(10, 20);
//		sum2(10);
//		sum2(10, 20);
		
//		rangeOper();
		
//		strIndex();
		
		listShow();
		
	}
	
	static void helloWorld() {
		int year = 2021;
		def word = year + '你好， Groovy 语言！';
		println(word);
		println new Date();
	}
	
	static void rangeOper() {
		def range = 5..10;
		println(range);
		println(range.get(2));
		
		for (num in range) {
			def desc = '当前数字';
			if(num == 7) {
				desc = '我喜爱的数字';
			}
			println(desc + '：' + num)
		}
	}
	
	
	/**
	 * Groovy 的特有方式
	 * 
	 * @param num1
	 * @param num2
	 * @return
	 */
	static def int sum(num1, num2 = 9) {
		int sum = num1 + num2;
		println(sum);
		return sum;
	}
	
	/**
	 * 兼容Java的方式
	 * 
	 * @param num1
	 * @param num2
	 * @return
	 */
	public static int sum2(int num1, int num2 = 9) {
		int sum = num1 + num2;
		println(sum);
		return sum;
	}
	
	/**
	 * 字符串索引
	 * 
	 * @return
	 */
	static def strIndex() {
		String str = 'Hello World 2021!';
		println(str[4]);
		println(str[1..2]);
		println(str[4..2]);
	}
	
	static def listShow() {
		def list1 = [];
		list1.add(2021);
		list1.add('新的方式');
		
		println(list1);
		
		def list2 = [12, 13];
		def list3 = [11, 2, 33, 12, 13, 16];
		println list3.minus(list2);
		
		def mp = ["TopicName" : "Maps", "TopicDescription" : "Methods in Maps"]
		println(mp.containsKey("TopicName"));
	}
}
