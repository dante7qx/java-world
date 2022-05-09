package org.java.world.dante.serializable.kryo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.java.world.dante.serializable.SerializableTests;
import org.java.world.dante.serializable.vo.UserVO;
import org.junit.Test;
import org.objenesis.strategy.StdInstantiatorStrategy;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import lombok.extern.slf4j.Slf4j;

/**
 * 速度快，序列化后体积小（Java平台）
 * 
 * @author dante
 *
 */
@Slf4j
public class KyroTests extends SerializableTests {

	@Test
	public void serializable() throws FileNotFoundException {
		Kryo kryo = new Kryo();
		kryo.setReferences(false);
		kryo.setRegistrationRequired(false);
		kryo.setInstantiatorStrategy(new StdInstantiatorStrategy());
		kryo.register(UserVO.class);
		Output output = new Output(new FileOutputStream(KYRO_FIEL));
		for (UserVO user : users) {
			kryo.writeObject(output, user);
		}
		output.flush();
		output.close();
	}

	@Test
	public void deserialization() throws FileNotFoundException {
		Kryo kryo = new Kryo();
		kryo.setReferences(false);
		kryo.setRegistrationRequired(false);
		kryo.setInstantiatorStrategy(new StdInstantiatorStrategy());
		Input input = new Input(new FileInputStream(KYRO_FIEL));
		UserVO user = null;
		while ((user = kryo.readObject(input, UserVO.class)) != null) {
			log.info("{}", user);
		}
		input.close();
	}

}
