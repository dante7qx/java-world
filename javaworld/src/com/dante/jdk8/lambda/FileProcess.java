package com.dante.jdk8.lambda;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileProcess {

	/**
	 * 读取文件
	 * 
	 * @param p 行为参数
	 * @return
	 * @throws IOException
	 */
	public static String processFile(BufferedReaderProcessor p) throws IOException {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader("/Users/dante/Documents/Project/javaworld/src/com/dante/jdk8/lambda/data.txt"));
			return p.process(br);
		} finally {
			br.close();
		}
	}
	
	public static void main(String[] args) throws IOException {
		// 使用Lambda表达式传递函数式接口
		System.out.println(processFile((BufferedReader br) -> br.readLine()));
		
		System.out.println(processFile((BufferedReader br) -> {
			return br.readLine() + "\n" + br.readLine();
		}));
	}
	
	
}
