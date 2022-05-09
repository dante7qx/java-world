package com.dante.arithmetic.sort;

import java.util.Arrays;
import java.util.Random;

/**
 * 希尔排序
 * http://blog.csdn.net/jianyuerensheng/article/details/51258460
 * 
 * 也称递减增量排序算法，是插入排序的一种高速而稳定的改进版本。
 * 希尔排序是基于插入排序的以下两点性质而提出改进方法的： 
 * （1）插入排序在对几乎已经排好序的数据操作时， 效率高， 即可以达到线性排序的效率 
 * （2）但插入排序一般来说是低效的， 因为插入排序每次只能将数据移动一位
 * 
 * 原理：
 * Shell排序通过将数据分成不同的组，先对每一组进行排序，然后再对所有的元素进行一次插入排序，以减少数据交换和移动的次数。
 * 平均效率是O(nlogn)。其中分组的合理性会对算法产生重要的影响。现在多用D.E.Knuth的分组方法。
 * 
 * 所谓的基本有序，就是小的关键字基本在前面，大的基本在后面，不大不小的基本在中间，
 * 例如｛2,1,3,6,4,7,5,8,9,｝就可以称为基本有序了。但像｛1,5,9,3,7,8,2,4,6｝这样，9在第三位，2在倒数第三位就谈不上基本有序。
 * 
 * 性能；
 * Shell排序比冒泡排序快5倍，比插入排序大致快2倍。
 * Shell排序比起QuickSort，MergeSort，HeapSort慢很多。
 * 
 * 适合于数据量在5000以下并且速度并不是特别重要的场合。对于数据量较小的数列重复排序是非常好的。
 * 
 * @author dante
 *
 */
public class ShellSort {
	
	public static void shellSort(int[] array) {
		int j = 0;
        int temp = 0;
        for (int increment = array.length / 2; increment > 0; increment /= 2) {
//          System.out.println("increment:" + increment);
            for (int i = increment; i < array.length; i++) {
                // System.out.println("i:" + i);
                temp = array[i];
                for (j = i - increment; j >= 0; j -= increment) {
                    if (temp < array[j]) {
                        array[j + increment] = array[j];
                    } else {
                        break;
                    }
                }
                array[j + increment] = temp;
            }
            /*
            for (int i = 0; i < array.length; i++) {
                System.out.print(array[i] + " ");
            }
            */
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
		shellSort(array);
		System.out.println(Arrays.toString(array));
		long end = System.currentTimeMillis();
		System.out.println(end - start);
	}
}
