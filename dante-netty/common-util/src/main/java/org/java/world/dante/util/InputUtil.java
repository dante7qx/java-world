package org.java.world.dante.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access=AccessLevel.PRIVATE)
public class InputUtil {

	// 键盘输入
	private static final BufferedReader KEYBOARD_INPUT = new BufferedReader(new InputStreamReader(System.in));
	
	public static String getUserInput() {
		String str = "";
		boolean flag = true;
		while(flag) {
			try {
				str = KEYBOARD_INPUT.readLine();
				if("".equals(str) || str == null) {
					throw new IOException("请至少输入一个字符！");
				}
				flag = false;
			} catch (IOException e) {
				log.info("用户输入错误。", e);
			}
		}
		return str;
	}
	
}
