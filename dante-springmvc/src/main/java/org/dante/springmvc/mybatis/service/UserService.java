package org.dante.springmvc.mybatis.service;

import java.util.List;
import java.util.Map;

import org.dante.springmvc.mybatis.domain.Permission;
import org.dante.springmvc.mybatis.domain.Role;
import org.dante.springmvc.mybatis.domain.User;

public interface UserService {
	public User findByUserName(String userName) throws Exception;
	
	public User findById(Integer id) throws Exception;

	public List<Role> findUserRolesByUserName(String userName) throws Exception;

	public List<Permission> findUserPermissionByUserName(String userName) throws Exception;
	
	public List<User> findUsers(Map<String, Object> filterMap) throws Exception;
	
	public User insertUser(String userName, String password) throws Exception;
	
	public void insertUser(User user) throws Exception;
	
	public void deleteUser(Integer id) throws Exception;
	
	public void updateUser(User user) throws Exception;
}
