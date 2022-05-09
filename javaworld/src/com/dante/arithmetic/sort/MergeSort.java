package com.dante.arithmetic.sort;

import java.util.Arrays;
import java.util.Random;

/**
 * 归并排序
 * 
 * 参考：http://blog.csdn.net/jianyuerensheng/article/details/51262984
 * 
 * 定义：
 * 对于给定的一组记录，利用递归与分治技术将数据序列划分成为越来越小的半子表，
 * 在对半子表排序，最后再用递归方法将排好序的半子表合并成为越来越大的有序序列。
 * 1. 划分子表（半分法）
 * 2. 合并半子表
 * 
 * 算法：
 * 经过第一轮比较后得到最小的记录，然后将该记录的位置与第一个记录的位置交换；
 * 接着对不包括第一个记录以外的其他记录进行第二次比较，得到最小记录并与第二个位置记录交换；重复该过程，知道进行比较的记录只剩下一个为止。
 * 
 * （1）申请空间，使其大小为两个已经排序序列之和，该空间用来存放合并后的序列 
 * （2）设定两个指针，最初位置分别为两个已经排序序列的起始位置 
 * （3）比较两个指针所指向的元素，选择相对小的元素放入到合并空间，并移动指针到下一位置 
 * （4）重复步骤3直到某一指针达到序列尾 （递归）
 * （5）将另一序列剩下的所有元素直接复制到合并序列尾
 * 
 * 适用场景：
 * 归并排序在数据量比较大的时候也有较为出色的表现（效率上）。
 * 但是，其空间复杂度O(n)使得在数据量特别大的时候（例如，1千万数据）几乎不可接受。
 * 而且，考虑到有的机器内存本身就比较小，因此，采用归并排序一定要注意
 * 
 * @author dante
 *
 */
public class MergeSort {

	/**
	 * 合并半子表
	 * 
	 * @param array
	 * @param low 开始位置
	 * @param mid 数组半分位置，一般是 (low + high) / 2【更严谨，考虑 high 的范围】
	 * @param high 结束位置
	 */
	public static void merge(int[] array, int low, int mid, int high) {
		// 申请空间，使其大小为两个已经排序序列之和，该空间用来存放合并后的序列
		int[] temp = new int[high - low + 1];
		
		// 左指针, 排序序列 1 的起始位置
		int left = low;
		// 右指针，排序序列 2 的起始位置
		int right = mid + 1;
		// 申请空间的下标
		int i = 0; 
		// 比较两个指针所指向的元素，选择相对小的元素放入到合并空间，并移动指针到下一位置 
		// 移动左右的指针
		while (left <= mid && right <= high) {
			// 将小的元素放倒合并空间
			if(array[left] < array[right]) {
				temp[i++] = array[left++];
			} else {
				temp[i++] = array[right++];
			}
		}
		
		// 将另一序列剩下的所有元素直接复制到合并序列尾
		// 把左边剩余的数移入 temp 数组
		while(left <= mid) {
			temp[i++] = array[left++];
		}
		
		// 把右边边剩余的数移入 temp 数组
		while(right <= high) {
			temp[i++] = array[right++];
		}
		
		// 把新数组中的数覆盖nums数组
		for (int k2 = 0; k2 < temp.length; k2++) {
            array[k2 + low] = temp[k2];
        }
	}
	
	/**
	 * 划分子表（半分、递归）
	 * 
	 * @param array
	 * @param low
	 * @param high
	 */
	public static void mergeSort(int[] array, int low, int high) {
		// 半分子表
		int mid = (low + high) / 2;
		
		if(low < high) {
			// 左边
            mergeSort(array, low, mid);
            // 右边
            mergeSort(array, mid + 1, high);
            // 左右归并
            merge(array, low, mid, high);
            
//            System.out.println("排序 （" + low + "," + mid + "," + high + "）" + Arrays.toString(array));
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
		long start = System.currentTimeMillis();
		mergeSort(array, 0, array.length - 1);
        System.out.println("排序结果：" + Arrays.toString(array));
        long end = System.currentTimeMillis();
		System.out.println(end - start);
	}

}
