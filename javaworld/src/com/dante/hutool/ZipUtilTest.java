package com.dante.hutool;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.junit.jupiter.api.Test;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ZipUtil;

public class ZipUtilTest {
	
	String DIR = "/Users/dante/Documents/Project/java-world/javaworld/javaworld/src/com/dante/hutool/";
	String[] files = new String[] { "1.txt",  "2.txt",  "3.txt" };
	
	@Test
	public void test() throws FileNotFoundException {
		InputStream[] insArr = new FileInputStream[files.length];
		
		for (int i = 0; i < files.length; i++) {
			insArr[i] = new FileInputStream(DIR + files[i]);
		}
		
		ZipUtil.zip(FileUtil.file("/Users/dante/Desktop/txt.zip"), files, insArr);
	}
	
	
}
