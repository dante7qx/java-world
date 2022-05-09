package com.dante.arithmetic.sort;

import java.util.Arrays;
import java.util.Random;

/**
 * 快速排序
 * 
 * 基本原理：
 * 对于给定的一组记录，选择一个基准元素,通常选择第一个元素或者最后一个元素,通过一趟扫描，将待排序列分成两部分,
 * 一部分比基准元素小,一部分大于等于基准元素, 此时基准元素在其排好序后的正确位置,然后再用同样的方法递归地排序划分的两部分，
 * 直到序列中的所有记录均有序为止
 *
 * 步骤：
 * （1） 如果不多于1个数据，直接返回。 
 * （2） 一般选择序列最左边的值作为支点数据。 
 * （3） 将序列分成2部分，一部分都大于支点数据，另外一部分都小于支点数据。 
 * （4） 对两边利用递归排序数列。
 * 
 * @author dante
 *
 */
public class QuickSort {

	public static void sort(int a[], int low, int hight) {
		int i, j, index;
		if (low > hight) {
			return;
		}
		i = low;
		j = hight;
		index = a[i]; // 用子表的第一个记录做基准
		while (i < j) { // 从表的两端交替向中间扫描
			while (i < j && a[j] >= index)
				j--;
			if (i < j)
				a[i++] = a[j];// 用比基准小的记录替换低位记录
			while (i < j && a[i] < index)
				i++;
			if (i < j) // 用比基准大的记录替换高位记录
				a[j--] = a[i];
		}
		a[i] = index;// 将基准数值替换回 a[i]
		sort(a, low, i - 1); // 对低子表进行递归排序
		sort(a, i + 1, hight); // 对高子表进行递归排序

	}

	public static void quickSort(int a[]) {
		sort(a, 0, a.length - 1);
	}

	private static int[] buildrandomArray(int size) {
		int[] arr = new int[size];
		Random r = new Random();
		for (int i = 0; i < size; i++) {
			arr[i] = (r.nextInt(1000));
		}
		return arr;
	}

	public static void main(String[] args) {
		int[] array = buildrandomArray(500000);
		// 从小到大排序
		long start = System.currentTimeMillis();
		quickSort(array);
		System.out.println(Arrays.toString(array));
		long end = System.currentTimeMillis();
		System.out.println(end - start);
	}

}
