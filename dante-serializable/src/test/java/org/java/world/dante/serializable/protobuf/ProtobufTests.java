package org.java.world.dante.serializable.protobuf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.java.world.dante.serializable.SerializableTests;
import org.java.world.dante.serializable.protobuf.UserProtos.User;
import org.java.world.dante.serializable.protobuf.UserProtos.UserList;
import org.junit.Test;

import com.google.protobuf.Timestamp;

import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProtobufTests extends SerializableTests {

	@Test
	public void serializable() throws IOException  {
		UserList.Builder userList = UserList.newBuilder();
		try {
			userList.mergeFrom(new FileInputStream(PROTOBUF_FIEL));
		} catch (FileNotFoundException e) {
			File file = new File(PROTOBUF_FIEL);
			file.createNewFile();
			log.error("FileNotFoundException", e);
		}		
		for (int i = 0; i < count; i++) {
			userList.addUser(
				User.newBuilder()
					.setId(i)
					.setName("Protobuf-" + i)
					.setBirthday(Timestamp.getDefaultInstance())
					.setSex(i % 2 == 0 ? "F" : "M")
				.build());
		}
		@Cleanup FileOutputStream out = new FileOutputStream(PROTOBUF_FIEL);
		userList.build().writeTo(out);
	}
	
	@Test
	public void deserialization() throws IOException {
		@Cleanup FileInputStream is = new FileInputStream(PROTOBUF_FIEL);
		UserList userList = UserList.parseFrom(is);
		List<User> users = userList.getUserList();
		for (User user : users) {
			log.info("{}", user);
		}
	}
	
}
