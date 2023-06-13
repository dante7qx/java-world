package com.dante.arithmetic.signature;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.Signature;
import java.security.spec.ECGenParameterSpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * ECDSA是椭圆曲线数字签名算法（Elliptic Curve Digital Signature Algorithm）的简称，是使用椭圆曲线密码（ECC）对数字签名算法（DSA）的模拟。
 * ECDSA于1999年成为ANSI标准，并于2000年成为IEEE和NIST标准。它在1998年既已为ISO所接受，并且包含它的其他一些标准亦在ISO的考虑之中。
 * 
 * Jdk15+ 不支持
 * 
 * @author dante
 *
 */
public class ECDSATest {

	public static void main(String[] args) throws Exception {
		secp256k1();
	}
	
	private static void secp256k1() throws Exception {
		// 在生成密钥对之前，添加BouncyCastleProvider 
				Security.addProvider(new BouncyCastleProvider());
				// 生成公钥和私钥对
		        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC");
		        keyPairGenerator.initialize(new ECGenParameterSpec("secp256k1"));
		        KeyPair keyPair = keyPairGenerator.generateKeyPair();
		        PublicKey publicKey = keyPair.getPublic();
		        PrivateKey privateKey = keyPair.getPrivate();

		        // 待签名的数据 
		        String data = "Hello, ECDSA!";

		        // 执行私钥签名
		        Signature signature = Signature.getInstance("SHA256withECDSA");
		        signature.initSign(privateKey);
		        signature.update(data.getBytes());
		        byte[] signBytes = signature.sign();

		        // 执行公钥验证
		        signature.initVerify(publicKey);
		        signature.update(data.getBytes());
		        boolean verified = signature.verify(signBytes);

		        // 输出验证结果
		        System.out.println("Verified: " + verified);
	}
	
}
