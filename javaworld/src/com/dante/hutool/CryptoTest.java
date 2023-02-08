package com.dante.hutool;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

import org.apache.axis.encoding.Base64;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;

public class CryptoTest {
	
	static final String content = "你好，World!";
	
	@Test
	public void symmetricEncrypt() {
		//随机生成密钥
		byte[] key = SecureUtil.generateKey(SymmetricAlgorithm.AES.getValue()).getEncoded();
		//构建
		SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES, key);
		//加密
		byte[] encrypt = aes.encrypt(content);
		// 解密
//		byte[] decrypt = aes.decrypt(encrypt);

		//加密为16进制表示
		String encryptHex = aes.encryptHex(content);
		//解密为字符串
		String decryptStr = aes.decryptStr(encryptHex, CharsetUtil.CHARSET_UTF_8);
		System.out.println(StrUtil.str(encrypt, CharsetUtil.CHARSET_UTF_8));
		System.out.println(decryptStr);
	}
	
	@Test
	public void rsaEncrypt() {
		KeyPair pair = SecureUtil.generateKeyPair("RSA");
		PrivateKey privateKey = pair.getPrivate();
		PublicKey publicKey = pair.getPublic();
		
		System.out.println("===== 公钥 =====");
		System.out.println(Base64.encode(publicKey.getEncoded()));
		
		System.out.println("===== 私钥 =====");
		System.out.println(Base64.encode(privateKey.getEncoded()));
		
		RSA rsa = new RSA();
		
		// 公钥加密，私钥解密
		byte[] encrypt = rsa.encrypt(StrUtil.bytes("我是一段测试aaaa", CharsetUtil.CHARSET_UTF_8), KeyType.PublicKey);
		byte[] decrypt = rsa.decrypt(encrypt, KeyType.PrivateKey);
		Assert.assertEquals("我是一段测试aaaa", StrUtil.str(decrypt, CharsetUtil.CHARSET_UTF_8));
		
		// 私钥加密，公钥解密
		byte[] encrypt2 = rsa.encrypt(StrUtil.bytes("我是一段测试aaaa", CharsetUtil.CHARSET_UTF_8), KeyType.PrivateKey);
		byte[] decrypt2 = rsa.decrypt(encrypt2, KeyType.PublicKey);
		Assert.assertEquals("我是一段测试aaaa", StrUtil.str(decrypt2, CharsetUtil.CHARSET_UTF_8));

	}
		
}
