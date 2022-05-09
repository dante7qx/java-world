package org.java.world.dante.serializable;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.java.world.dante.serializable.vo.UserVO;
import org.junit.Before;

public class SerializableTests {
	protected final int count = 100;
	protected List<UserVO> users = new ArrayList<>();
	
	protected final String JDK_FIEL = "jdk_serializable.data";
	protected final String PROTOBUF_FIEL = "protobuf_serializable.data";
	protected final String KYRO_FIEL = "kyro_serializable.data";

	@Before
	public void init() {
		for (int i = 0; i < count; i++) {
			users.add(new UserVO(i, "序列化-" + i, Date.from(Instant.now()), "F"));
		}
	}
}
