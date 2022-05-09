package com.dante.letcode;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出 和为目标值的那两个整数，并返回它们的数组下标。
 * 
 * 你可以假设每种输入只会对应一个答案。但是，数组中同一个元素不能使用两遍。
 * 
 * 你可以按任意顺序返回答案。
 * 
 * 示例 1：
 * 
 * 输入：nums = [2,7,11,15], target = 9 输出：[0,1] 解释：因为 nums[0] + nums[1] == 9 ，返回
 * [0, 1] 。 示例 2：
 * 
 * 输入：nums = [3,2,4], target = 6 输出：[1,2] 示例 3：
 * 
 * 输入：nums = [3,3], target = 6 输出：[0,1]
 **/
public class TwoSum {
	public static int[] twoSum(int[] nums, int target) {
		Set<Integer> ret = new HashSet<>();
		int len = nums.length;
		if (len == 0) {
			return new int[0];
		}
		for (int i = 0; i < len; i++) {
			int x = nums[i];
			for (int j = i + 1; j < len; j++) {
				if (x + nums[j] == target) {
					ret.add(i);
					ret.add(j);
				}
			}
		}
		Object[] array = ret.toArray();
		int[] retArr = new int[array.length];
		for (int i = 0; i < array.length; i++) {
			retArr[i] = (int) array[i];
		}
		return retArr;
	}

	public static void main(String[] args) {
		System.out.println(Arrays.toString(twoSum(new int[] { 2 , 3, 4, 5, 8, 3, 7, 6}, 9)));
	}
}
