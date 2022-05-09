package org.java.world.dante.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.java.world.dante.service.IHelloService;
import org.java.world.dante.vo.UserVO;

import com.google.inject.Singleton;

@Singleton
public class HelloService implements IHelloService {

	@Override
	public String sayHello() {
		return "Hello, World! - 你好，世界！";
	}

	@Override
	public List<UserVO> getUserList() {
		List<UserVO> users = new ArrayList<>();
		for (int i = 0; i < 20; i++) {
			users.add(new UserVO(UUID.randomUUID().toString().replaceAll("-", ""), "用户-" + i));
		}
		return users;
	}

}
