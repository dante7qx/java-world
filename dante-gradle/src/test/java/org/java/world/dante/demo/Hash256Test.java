package org.java.world.dante.demo;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Test;

import cn.hutool.core.codec.Base58;
import cn.hutool.core.lang.Console;
import cn.hutool.core.util.HashUtil;
import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;


public class Hash256Test {

	@Test
	public void encryptionAlgorithm() {
		// Python 结果：8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92
		
		Console.log(Objects.hash("123456"));
		Console.log("123456".hashCode());
		Console.log(Objects.hash("ABCDEF"));
		Console.log(HashUtil.rsHash("123456"));
		Console.log(HashUtil.rsHash("ABCDEF"));
		Console.log(DigestUtil.sha256Hex("ABCDEF"));
		// 使用SHA-256计算哈希码
		String hashBytes = DigestUtil.sha256Hex("123456".getBytes(StandardCharsets.UTF_8));
		Console.log(hashBytes);
		Console.log(hashSHA256("123456"));
		// 将字节数组的哈希码转换为十六进制字符串
		Console.log(HexUtil.encodeHexStr(hashBytes));
		
	}
	
	private String hashSHA256(String data) {
		String hash = "";
		try {
            // 创建SHA-256消息摘要对象
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // 计算数据的哈希码
            byte[] hashBytes = digest.digest(data.getBytes(StandardCharsets.UTF_8));

            // 将哈希码转换为十六进制字符串
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            // 哈希值
            hash = hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
		return hash;
	}
	
	@Test
	public void md5() {
		Console.log(DigestUtils.md5Hex("dante7qx")); 
	}
	
	public static void main(String[] args) {
		Console.log("Base58 -> {}", Base58.encode("output".getBytes()));
		String data = """
				OP_HASH256
				      DATA 6fe28c0ab6f1b372c1a6a246ae63f74f931e8365e15a089c68d6190000000000
				  OP_EQUAL
				""";
		var count = Math.pow(2, 32);
		for (int i = 1; i <= count; i++) {
			String sha256Hex = DigestUtil.sha256Hex((data + i).getBytes(StandardCharsets.UTF_8));
			if(StrUtil.startWith(sha256Hex, "0000000")) {
				Console.log("{} - {}", i, sha256Hex);
				break;
			}
		}
	}
	
}
