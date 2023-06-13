package com.dante.jdk8.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * 1、找出颜色是绿色的苹果
 * 2、找出重量大于100斤的苹果
 * 3、找出红色并且大于90斤的苹果
 * 4、按重量排序，然后按颜色分组
 * 
 * @author dante
 *
 */
public class FilteringApple {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<Apple> invertory = Arrays.asList(new Apple(150, "red"), new Apple(80, "green"), new Apple(85, "red"), new Apple(120, "yellow"));
		System.out.println(invertory);
		invertory.sort((p1, p2) -> p1.getWeight().compareTo(p2.getWeight()));
		System.out.println(invertory);
		// 按照苹果的重量倒序排列 
		invertory.sort(Comparator.comparing(Apple::getWeight).reversed());
		System.out.println(invertory);
		System.out.println("流式处理：" + invertory.stream()
					.filter(p -> p.getWeight() > 70)
					.map(Apple::getColor)
					.distinct()
					.collect(Collectors.toList()));
		invertory.parallelStream().forEach(a -> {
			System.out.println(a.getColor() + " -> " + a.getWeight());
		});
		System.out.println(invertory.stream().count());
		
		// 方法传递，语法：类(实例)::方法名
		List<Apple> greenApples2 = filterApples(invertory, FilteringApple::isGreenApple);
		System.out.println(greenApples2);
		
		// List<Apple> greenApples = filterApples(invertory, (Apple p) -> "green".equals(p.getColor()));
		// 可省略参数类型，Lambda 表达式
		List<Apple> greenApples = filterApples(invertory, p -> "green".equals(p.getColor()));
		System.out.println(greenApples);
		
		List<Apple> bigRedApples = filterApples(invertory, p -> "red".equals(p.getColor()) && p.getWeight() > 100);
		System.out.println(bigRedApples);
		
		// 排序分组
		Map<Integer, List<Apple>> orderApples = invertory.stream()
			.filter(p -> "red".equals(p.getColor()))
//			.collect(Collectors.groupingBy(Apple::getWeight));	// 无序map
			.collect(Collectors.groupingBy(Apple::getWeight, LinkedHashMap::new, Collectors.toList()));	// 有序map
		System.out.println(orderApples);
		
		System.out.println(invertory);
		consumer(invertory, (p) -> p.weight = 180);
		System.out.println(invertory);
		
		Supplier<Integer> random = new Random()::nextInt;
		for (int i = 0; i < 10; i++) {
			new Thread(() -> {
				System.out.print(Thread.currentThread().getId() + " - ");
				System.out.print(Thread.currentThread().getName() + " - ");
				System.out.println(random.get());
			}).start();
		}
		
		Supplier<Apple> suAp = Apple::new;
		System.out.println(suAp.get());
		
		Function<Integer, Apple> funcAp = Apple::new;
		System.out.println(funcAp.apply(200));
		
		BiFunction<Integer, String, Apple> biApple = Apple::new;
		System.out.println(biApple.apply(170, "blue"));
		
		// 找到重量最大的苹果
		Optional<Apple> maxApple = invertory.stream().collect(Collectors.maxBy(Comparator.comparing(Apple::getWeight)));
		System.out.println("最重的苹果：" + maxApple.get());
		
		// 所有苹果的总重量
		Integer sumWeight = invertory.stream().collect(Collectors.summingInt(Apple::getWeight));
		System.out.println("所有苹果的总重量：" + sumWeight);
		
		int totalWeight = invertory.stream().mapToInt(Apple::getWeight).sum();
		System.out.println("所有苹果的总重量：" + totalWeight);
		
		int reducingSum = invertory.stream().collect(Collectors.reducing(0, Apple::getWeight, Integer::sum));
		System.out.println("所有苹果的总重量：" + reducingSum);
		
		int totalGreenWeight = invertory.stream().filter(p -> "green".equals(p.getColor())).mapToInt(Apple::getWeight).sum();
		System.out.println("所有绿色苹果的总重量：" + totalGreenWeight);
		
		// 获取统计信息
		IntSummaryStatistics intSummary = invertory.stream().collect(Collectors.summarizingInt(Apple::getWeight));
		System.out.println("统计信息：" + intSummary);
		
		System.out.println("苹果颜色：" + invertory.stream().map(Apple::getColor).distinct().collect(Collectors.joining(" - ")));
		
		// Optional 使用
		Optional<Apple> oa = Optional.ofNullable(new Apple());
		System.out.println(oa.map(Apple::getWeight).orElse(11));
		System.out.println(oa.orElse(null));
		
	}
	
	public static boolean isGreenApple(Apple p) {
		return "green".equals(p.getColor());
	}
	
	static List<Apple> filterApples(List<Apple> invertory, Predicate<Apple> p) {
		List<Apple> result = new ArrayList<>();
		for (Apple apple : invertory) {
			if(p.test(apple)) {
				result.add(apple);
			}
		}
		return result;
	}
	
	public static void consumer(List<Apple> list, Consumer<Apple> c) {
		for (Apple p : list) {
			c.accept(p);
		}
	}

	public static class Apple {
		private int weight = 0;
		private String color = "";

		public Apple() {
		}
		
		public Apple(int weight) {
			this.weight = weight;
		}
		
		public Apple(int weight, String color) {
			this.weight = weight;
			this.color = color;
		}

		public Integer getWeight() {
			return weight;
		}

		public void setWeight(Integer weight) {
			this.weight = weight;
		}

		public String getColor() {
			return color;
		}

		public void setColor(String color) {
			this.color = color;
		}

		public String toString() {
			return "Apple{" + "color='" + color + '\'' + ", weight=" + weight + '}';
		}
	}

}
