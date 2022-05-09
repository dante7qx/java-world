package org.java.world.dante.service;

import java.util.List;

import org.java.world.dante.vo.UserVO;

public interface IHelloService {
	public String sayHello();
	
	public List<UserVO> getUserList();
}
