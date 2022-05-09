package com.dante.arithmetic.sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Bsort {
	
	public static void bubbleSort(int[] array) {
		int tmp = 0;
		int size = array.length;
		for(int i = 0; i < size - 1; i++) {
			for(int j=i+1; j < size; j++) {
				if(array[i] < array[j]) {
					tmp = array[i];
					array[i] = array[j];
					array[j] = tmp;
				}
			}
		}
		
	}
	
	private static int[] buildArray(int size) {
		int[] array = new int[size];
		Random random = new Random();
		for (int i = 0; i < size; i++) {
			array[i] = random.nextInt(15);
		}
		return array;
	}
	
	public static void main(String[] args) {
		int[] array = buildArray(20);
		bubbleSort(array);
		System.out.println(Arrays.toString(array));
		
	}
}
