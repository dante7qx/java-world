package org.dante.springmvc.mybatis.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dante.springmvc.mybatis.dao.RoleDao;
import org.dante.springmvc.mybatis.dao.UserDao;
import org.dante.springmvc.mybatis.domain.Permission;
import org.dante.springmvc.mybatis.domain.Role;
import org.dante.springmvc.mybatis.domain.User;
import org.dante.springmvc.mybatis.domain.UserRole;
import org.dante.springmvc.mybatis.service.UserService;
import org.dante.springmvc.spring.utils.CryptographyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserDao userDao;
	@Autowired
	private RoleDao roleDao;
	
	@Override
	public User findByUserName(String userName) throws Exception {
		return userDao.findByUserName(userName);
	}

	@Override
	@Transactional
	public User insertUser(String userName, String password) throws Exception {
		User user = new User();
		user.setUserName(userName);
		user.setPassword(CryptographyUtils.sha256Hash(password, CryptographyUtils.SALT));
		userDao.insertUser(user);
		return user;
	}

	@Override
	public List<Role> findUserRolesByUserName(String userName) throws Exception {
		return userDao.findUserRolesByUserName(userName);
	}

	@Override
	public List<Permission> findUserPermissionByUserName(String userName) throws Exception {
		return userDao.findUserPermissionByUserName(userName);
	}

	@Override
	public List<User> findUsers(Map<String, Object> filterMap) throws Exception {
		return userDao.findUsers(filterMap);
	}

	@Override
	public User findById(Integer id) throws Exception {
		User user = userDao.findById(id);
		List<Role> roles = userDao.findUserRolesByUserId(id);
		if(roles != null && !roles.isEmpty()) {
			Set<Integer> roleIds = new HashSet<Integer>();
			for (Role role : roles) {
				roleIds.add(role.getId());
			}
			user.setRoleIds(roleIds);
		}
		return user;
	}

	@Override
	@Transactional
	public void updateUser(User user) throws Exception {
		user.setPassword(CryptographyUtils.sha256Hash(user.getPassword(), CryptographyUtils.SALT));
		userDao.updateUser(user);
		userDao.deleteUserRoleByUserId(user.getId());
		Set<Integer> roleIds = user.getRoleIds();
		if(roleIds != null && !roleIds.isEmpty()) {
			for (Integer roleId : roleIds) {
				UserRole userRole = new UserRole();
				userRole.setRoleId(roleId);
				userRole.setUserId(user.getId());
				roleDao.insertUserRole(userRole);
			}
		}
	}

	@Override
	@Transactional
	public void insertUser(User user) throws Exception {
		user.setPassword(CryptographyUtils.sha256Hash(user.getPassword(), CryptographyUtils.SALT));
		userDao.insertUser(user);
		
		Set<Integer> roleIds = user.getRoleIds();
		if(roleIds != null && !roleIds.isEmpty()) {
			for (Integer roleId : roleIds) {
				UserRole userRole = new UserRole();
				userRole.setRoleId(roleId);
				userRole.setUserId(user.getId());
				roleDao.insertUserRole(userRole);
			}
		}
	}

	@Override
	@Transactional
	public void deleteUser(Integer id) throws Exception {
		userDao.deleteUserRoleByUserId(id);
		userDao.deleteUser(id);
	}

}
