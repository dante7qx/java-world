package com.dante.jdk12;

import java.text.NumberFormat;
import java.util.Locale;

public class Feature12 {

	public static void main(String[] args) {
		WeekEnum day = WeekEnum.MONDAY;
		switch (day) {
			case MONDAY, FRIDAY, SUNDAY -> System.out.println(6);
			case TUESDAY -> System.out.println(7);
			case THURSDAY, SATURDAY -> System.out.println(8);
			case WEDNESDAY -> System.out.println(9);
		}
		
		// 多行字符串
		String html = """
				<html>
				<body>
				  <p>Hello, World!</p>
				</body>
				</html>
				""";
		System.out.println(html);
		
		NumberFormat fmt = NumberFormat.getCompactNumberInstance(Locale.US, NumberFormat.Style.SHORT);
		System.out.println(fmt.format(1000));
		
		// instanceOf new Feature is not work
		/*
		Object str = "hello";
		if (str instanceof String s && "hello".equals(s)) {
			System.out.println(s.concat(" World"));
		}
		*/
	}
}
