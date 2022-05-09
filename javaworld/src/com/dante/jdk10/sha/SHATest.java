package com.dante.jdk10.sha;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.codec.binary.Hex;

/**
 * SHA3-224、SHA3-256、SHA3-384 和 S HA3-512。
 * 另外也增加了通过 java.security.SecureRandom 生成使用 DRBG 算法的强随机数。
 * 
 * @author dante
 *
 */
public class SHATest {
	public static void main(final String[] args) throws NoSuchAlgorithmException { 
        final MessageDigest instance = MessageDigest.getInstance("SHA3-224"); 
        final byte[] digest = instance.digest("Hello Java 10".getBytes()); 
        String encodeStr = Hex.encodeHexString(digest);
        System.out.println(encodeStr); 
    } 
}
