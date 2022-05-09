package org.java.world.dante.serializable.java;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

import org.java.world.dante.serializable.SerializableTests;
import org.java.world.dante.serializable.jdk.JdkSerializableUtils;
import org.java.world.dante.serializable.vo.UserVO;
import org.junit.Test;

import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;

/**
 * Java 原生序列化，缺点
 * （1）无法跨语言    
 * （2）序列化的码流太大   
 * （3）序列化的性能差
 * 
 * @author dante
 *
 */
@Slf4j
public class JavaSerializableTests extends SerializableTests {
	
	/**
	 * 序列化
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	@Test
	public void serializable() throws FileNotFoundException, IOException {
		File file = new File(JDK_FIEL);
		@Cleanup OutputStream os = new FileOutputStream(file);
		@Cleanup ObjectOutputStream oos = new ObjectOutputStream(os);
		UserVO[] obj = new UserVO[users.size()];
		users.toArray(obj);
		oos.writeObject(obj);
		oos.flush();
	}
	
	
	/**
	 * 反序列化
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	@Test
	public void deserialization() throws IOException, ClassNotFoundException {
		File file = new File(JDK_FIEL);
		@Cleanup InputStream is = new FileInputStream(file);
		@Cleanup ObjectInputStream ois = new ObjectInputStream(is);
		
		UserVO[] obj = (UserVO[]) ois.readObject();
		List<UserVO> userList = Arrays.asList(obj);
		log.info("Users => {}", userList);
	}
	
	@Test
	public void serializableUtils() {
		File file = new File(JDK_FIEL);
		JdkSerializableUtils.writeObject(users, file);
		List<UserVO> userList = JdkSerializableUtils.readObjectForList(file);
		log.info("Users => {}", userList);
	}
	
}
