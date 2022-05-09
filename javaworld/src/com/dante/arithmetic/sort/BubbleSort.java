package com.dante.arithmetic.sort;

import java.util.Arrays;
import java.util.Random;

/**
 * 冒泡排序
 * 
 * 基本思想： 
 * 冒泡排序顾名思义就是整个过程像气泡一样往上升，单向冒泡排序的基本思想是（假设由小到大排序）：
 * 对于给定n个记录，从第一个记录开始依次对相邻的两个记录进行比较，当前面的记录大于后面的记录时，交换位置，进行一轮比较和换位后，
 * n个记录的最大记录将位于第n位，然后对前（n-1）个记录进行第二轮比较；重复该过程，直到记录剩下一个为止。
 * 
 * 步骤：
 * （1）比较相邻的元素。如果第一个比第二个大，就交换他们两个。 
 * （2）对每一对相邻元素作同样的工作，从开始第一对到结尾的最后一对。在这一点，最后的元素应该会是最大的数。 
 * （3）针对所有的元素重复以上的步骤，除了最后一个。 
 * （4）持续每次对越来越少的元素重复上面的步骤，直到没有任何一对数字需要比较。
 * 
 * @author dante
 *
 */
public class BubbleSort {

	public static void bubbleSort(int[] array) {
		int temp = 0;
		int arrLen = array.length;
		for (int i = 0; i < arrLen - 1; i++) {
			// 第一个记录和相邻的记录进行比较
			for (int j = i+1; j < arrLen; j++) {
				if (array[i] > array[j]) {
					// 记录大的
					temp = array[i];			
					// 数据内记录换位
					array[i] = array[j];	    // 小的换到前面
					array[j] = temp;			// 大的换到后面
				}
			}
		}
	}
	
	private static int[] buildRandomArray(int size) {
		int[] arr = new int[size];
		Random r = new Random();
		for (int i = 0; i < size; i++) {
			arr[i] = (r.nextInt(1000));
		}
		return arr;
	}

	public static void main(String[] args) {
		int[] array = buildRandomArray(500);
		// 从小到大排序
		long start = System.currentTimeMillis();
		bubbleSort(array); 
		System.out.println(Arrays.toString(array));
		long end = System.currentTimeMillis();
		System.out.println(end - start);
	}
}
