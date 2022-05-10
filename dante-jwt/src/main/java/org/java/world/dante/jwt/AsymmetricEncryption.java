package org.java.world.dante.jwt;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;

public class AsymmetricEncryption {

	public static final String KEY_ALGORITHM = "RSA";
	private static final String PUBLIC_KEY = "RSAPublicKey";
	private static final String PRIVATE_KEY = "RSAPrivateKey";
	
	private static final String KEY_PATH = "/Users/dante/Documents/Project/java-world/javaworld/dante-jwt/src/main/resources/";
	
	private static final String ENCRYPT_DATA = "/Users/dante/Documents/Project/java-world/javaworld/dante-jwt/src/main/resources/data.data";

	/**
	 * 生成公钥和私钥
	 * 
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> initKey() throws Exception {
		// 获得对象 KeyPairGenerator 参数 RSA 2048个字节
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
		keyPairGen.initialize(2048);
		// 通过对象 KeyPairGenerator 获取对象KeyPair
		KeyPair keyPair = keyPairGen.generateKeyPair();

		// 通过对象 KeyPair 获取RSA公私钥对象RSAPublicKey RSAPrivateKey
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		// 公私钥对象存入map中
		Map<String, Object> keyMap = new HashMap<String, Object>(2);
		keyMap.put(PUBLIC_KEY, publicKey);
		keyMap.put(PRIVATE_KEY, privateKey);
		
		// 把公私钥保存到硬盘上
		saveKey(publicKey, KEY_PATH + File.separator + "public_key");
		saveKey(privateKey, KEY_PATH + File.separator + "private_key");
		return keyMap;
	}
	
	/**
	 * 获取公钥
	 * 
	 * @param keyMap
	 * @return
	 * @throws Exception
	 */
	public static String getPublicKey(Map<String, Object> keyMap) throws Exception {
		Key key = (Key) keyMap.get(PUBLIC_KEY);
		return Base64.getEncoder().encodeToString(key.getEncoded());
	}
	
	/**
	 * 获取私钥
	 * 
	 * @param keyMap
	 * @return
	 * @throws Exception
	 */
    public static String getPrivateKey(Map<String, Object> keyMap) throws Exception {
        //获得map中的私钥对象 转为key对象
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        //编码返回字符串
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }


	

	/**
	 * 加密的方法,使用公钥进行加密
	 * 
	 * @throws Exception
	 */
	public static void publicEnrypy() throws Exception {

		Cipher cipher = Cipher.getInstance("RSA");
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
		// 生成钥匙对
		KeyPair keyPair = keyPairGenerator.generateKeyPair();
		// 得到公钥
		Key publicKey = keyPair.getPublic();
		// 得到私钥
		Key privateKey = keyPair.getPrivate();

		// 设置为加密模式
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);

		// 对数据进行加密
		byte[] result = cipher.doFinal("aaa".getBytes());

		System.out.println("=============" + new String(result));

		// 把私钥保存到硬盘上
		saveKey(privateKey, PRIVATE_KEY);
		saveKey(publicKey, PUBLIC_KEY);

		// 把加密后的数据保存到硬盘上
		saveData(result);
	}

	/**
	 * 解密的方法，使用私钥进行解密
	 * 
	 * @throws Exception
	 */
	public static void privateEncode() throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");

		// 从硬盘中读取私钥
		Key privateKey = loadKey();

		// 设置为解密模式，用私钥解密
		cipher.init(Cipher.DECRYPT_MODE, privateKey);

		FileInputStream fileInputStream = new FileInputStream(new File(ENCRYPT_DATA));

		CipherInputStream cipherInputStream = new CipherInputStream(fileInputStream, cipher);

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = cipherInputStream.read(buffer)) != -1) {
			{
				outputStream.write(buffer, 0, len);
			}
			cipherInputStream.close();

			System.out.println(outputStream.toString());
		}

		// cipherInputStream.
		// 从硬盘中读取加密后的数据
		// byte[] data = loadData();

		// 对加密后的数据进行解密，返回解密后的结果
		// byte[] result = cipher.doFinal(data);

		// System.out.println(new String(result));
	}

	/**
	 * 从硬盘中加载加密后的文件
	 * 
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	@SuppressWarnings("unused")
	private static byte[] loadData() throws FileNotFoundException, IOException {
		FileInputStream fileInputStream = new FileInputStream(new File(ENCRYPT_DATA));
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

		byte[] buffer = new byte[1024];
		int len = 0;

		while ((len = fileInputStream.read(buffer)) != -1) {
			outputStream.write(buffer, 0, len);
		}

		fileInputStream.close();

		return outputStream.toByteArray();
	}

	/**
	 * 从硬盘中加载私钥
	 * 
	 * @return
	 * @throws IOException
	 * @throws FileNotFoundException
	 * @throws ClassNotFoundException
	 */
	private static Key loadKey() throws IOException, FileNotFoundException, ClassNotFoundException {
		ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(new File(PRIVATE_KEY)));
		Key privateKey = (Key) inputStream.readObject();
		return privateKey;
	}

	/**
	 * 把加密后的就过保存到硬盘上
	 * 
	 * @param result
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private static void saveData(byte[] result) throws FileNotFoundException, IOException {
		FileOutputStream fileOutputStream = new FileOutputStream(new File(ENCRYPT_DATA));
		fileOutputStream.write(result);
	}

	/**
	 * 把私钥保存到硬盘上
	 * 
	 * @param privateKey
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	private static void saveKey(Key privateKey, String Path) throws IOException, FileNotFoundException {
		ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(new File(Path)));
		outputStream.writeObject(privateKey);
		outputStream.close();
	}

	public static void main(String[] args) throws Exception {
		 publicEnrypy();
		 privateEncode();
		
		/*
		try {
			Map<String, Object> keyMap = initKey();
			System.out.println(getPublicKey(keyMap));
			System.out.println(getPrivateKey(keyMap));
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
	}
}
