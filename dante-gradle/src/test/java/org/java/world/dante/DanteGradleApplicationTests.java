package org.java.world.dante;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import org.java.world.dante.dao.StudentDao;
import org.java.world.dante.po.StudentPO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import cn.hutool.core.lang.Console;
import cn.hutool.core.util.HashUtil;
import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
class DanteGradleApplicationTests {

	@Autowired
	private StudentDao studentDao;
	
	@Test
	public void findAll() {
		StudentPO student = new StudentPO();
		student.setName("测试");
		student.setAge(new Random().nextInt(39 - 21 + 1) + 21);
		student.setUpdateDate(Date.from(Instant.now()));
		studentDao.save(student);
		
		List<StudentPO> students = studentDao.findAll();
		log.info("{} ==> ", students);
	}
	
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
	
	public static void main(String[] args) {
		String data = "hello";
		for (int i = 1; i <= 256; i++) {
			String sha256Hex = DigestUtil.sha256Hex((data + i).getBytes(StandardCharsets.UTF_8));
			if(StrUtil.startWith(sha256Hex, "00")) {
				Console.log("{} - {}", i, sha256Hex);
				break;
			}
			
		}
	}
	
}
