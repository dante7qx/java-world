package com.dante.arithmetic.sort;

import java.util.Arrays;
import java.util.Random;

/**
 * 插入排序
 * 
 * http://blog.csdn.net/jianyuerensheng/article/details/51254415
 * 
 * 基本原理：
 * 通过构建有序序列，对于未排序数据，在已排序序列中从后向前扫描，找到相应位置并插入。
 * 插入排序在实现上，通常采用in-place排序（即只需用到O(1)的额外空间的排序），因而在从后向前扫描过程中，需要反复把已排序元素逐步向后挪位，
 * 为最新元素提供插入空间。 
 * 插入排序是对冒泡排序的改进。它比冒泡排序快2倍。一般不用在数据大于1000的场合下使用插入排序，或者重复排序超过200数据项的序列。 
 * 
 * 对于给定的一组记录，初始时假定第一个记录自成一个有序序列，其余记录为无序序列。
 * 接着从第二个记录开始，按照记录的大小依次将当前处理的记录插入到其之前的有序序列中，直到最后一个记录插到有序序列中为止。
 * 
 * 步骤： 
 *（1）从第一个元素开始，该元素可以认为已经被排序 
 *（2）取出下一个元素，在已经排序的元素序列中从后向前扫描 
 *（3）如果该元素（已排序）大于新元素，将该元素移到下一位置 
 *（4）重复步骤3，直到找到已排序的元素小于或者等于新元素的位置，将新元素插入到该位置中 
 *（5）重复步骤2
 * 
 * @author dante
 *
 */
public class InsertSort {

	public static void insertSort(int[] array) {
		int i, j;
		// 要插入的数据
		int insertNode;
		// 从数组的第二个元素开始循环将数组中的元素插入
		for (i = 1; i < array.length; i++) {
			// 设置数组中的第2个元素为第一次循环要插入的数据
			insertNode = array[i];
			j = i - 1;
			while (j >= 0 && insertNode < array[j]) {
				// 如果要插入的元素小于第j个元素,就将第j个元素向后移动
				array[j + 1] = array[j];
				j--;
			}
			// 直到要插入的元素不小于第j个元素,将insertNote插入到数组中
			array[j + 1] = insertNode;
		}
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
		insertSort(array);
		System.out.println(Arrays.toString(array));
		long end = System.currentTimeMillis();
		System.out.println(end - start);
	}

}
