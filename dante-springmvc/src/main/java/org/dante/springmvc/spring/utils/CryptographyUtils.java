package org.dante.springmvc.spring.utils;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.crypto.hash.Sha512Hash;

public class CryptographyUtils {
	
	public final static String SALT = "DANTE-SHIRO";
	
	/**
	 * Base64编码
	 * 
	 * @param str
	 * @return
	 */
	public static String encodeBase64(String str) {
		return Base64.encodeToString(str.getBytes());
	}
	
	/**
	 * Base64解码
	 * 
	 * @param str
	 * @return
	 */
	public static String decodeBase64(String str) {
		return Base64.decodeToString(str);
	}
	
	/**
	 * Md5加盐编码
	 * 
	 * @param str
	 * @param salt
	 * @return
	 */
	public static String md5(String str, String salt) {
		return md5(str, salt, 1);
	}
	
	/**
	 * Md5加盐编码
	 * 
	 * @param str
	 * @param salt
	 * @return
	 */
	public static String md5(String str, String salt, int hashIterations) {
		return new Md5Hash(str, salt, hashIterations).toString();
	}
	
	/**
	 * sha256加盐编码
	 * 
	 * @param str
	 * @param salt
	 * @return
	 */
	public static String sha256Hash(String str, String salt) {
		return new Sha256Hash(str, salt).toString();
	}
	
	/**
	 * sha512加盐编码
	 * 
	 * @param str
	 * @param salt
	 * @return
	 */
	public static String sha512Hash(String str, String salt) {
		return new Sha512Hash(str, salt).toString();
	}
	
	public static void main(String[] args) {
		String pwd = "iamdante";
		System.out.println(sha256Hash(pwd, SALT));
	}
	
}
