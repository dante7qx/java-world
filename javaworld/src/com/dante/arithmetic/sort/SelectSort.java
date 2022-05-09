package com.dante.arithmetic.sort;

import java.util.Arrays;
import java.util.Random;

/**
 * 选择排序
 * 
 * 工作原理： 
 * 首先在未排序序列中找到最小元素，存放到排序序列的起始位置，
 * 然后，再从剩余未排序元素中继续寻找最小元素，然后放到排序序列末尾。以此类推，直到所有元素均排序完毕。
 * 
 * @author dante
 *
 */
public class SelectSort {

	public static void selectSort(int[] a) {
		if (a == null || a.length <= 0) {
			return;
		}
		for (int i = 0; i < a.length; i++) {
			int temp = a[i];
			int flag = i; // 将当前下标定义为最小值下标
			for (int j = i + 1; j < a.length; j++) {
				if (a[j] < temp) {// a[j] < temp 从小到大排序；a[j] > temp 从大到小排序
					temp = a[j];
					flag = j; // 如果有小于当前最小值的关键字将此关键字的下标赋值给flag
				}
			}
			if (flag != i) {
				a[flag] = a[i];
				a[i] = temp;
			}
		}
	}
	
	private static int[] buildrandomArray(int size) {
		int[] arr = new int[size];
		Random r = new Random();
		for (int i = 0; i < size; i++) {
			arr[i] = (r.nextInt(10000));
		}
		return arr;
	}

	public static void main(String[] args) {
		int[] array = buildrandomArray(100000);
		long start = System.currentTimeMillis();
		selectSort(array);
		System.out.println(Arrays.toString(array));
		long end = System.currentTimeMillis();
		System.out.println(end - start);
	}
}
