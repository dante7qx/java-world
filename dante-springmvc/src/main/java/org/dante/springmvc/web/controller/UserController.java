package org.dante.springmvc.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.dante.springmvc.mybatis.domain.User;
import org.dante.springmvc.mybatis.service.UserService;
import org.dante.springmvc.spring.exception.DanteException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController("SysUserController")
@RequestMapping("/sysuser")
public class UserController {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private UserService userService;

	@RequiresPermissions("user:query")
	@RequestMapping(value = "/query", method = RequestMethod.POST, produces = "application/json")
	public List<User> findUsers(User user) throws DanteException {
		List<User> users = null;
		try {
			Map<String, Object> filterMap = new HashMap<String, Object>();
			if (!StringUtils.isEmpty(user.getUserName())) {
				filterMap.put("userName", "%" + user.getUserName() + "%");
			}
			users = userService.findUsers(filterMap);
		} catch (Exception e) {
			logger.error("获取用户失败", e);
		}
		return users;
	}
	
	@RequestMapping(value = "/queryById", method = RequestMethod.POST, produces = "application/json")
	public User findById(Integer id) {
		User user = null;
		try {
			user = userService.findById(id);
		} catch (Exception e) {
			logger.error("获取User失败"+id, e);
		}
		return user;
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST, produces = "application/json")
	public Map<String, Object> updateUser(User user) {
		Map<String, Object> result = new HashMap<String, Object>();
		boolean flag = false;
		try {
			if (user.getId() == null) {
				userService.insertUser(user);
			} else {
				userService.updateUser(user);
			}
			flag = true;
		} catch (Exception e) {
			logger.error("更新用户失败", e);
		}
		result.put("data", user);
		result.put("flag", flag);
		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST, produces = "application/json")
	public Map<String, Object> deleteById(Integer id) {
		Map<String, Object> result = new HashMap<String, Object>();
		boolean flag = false;
		try {
			userService.deleteUser(id);
			flag = true;
		} catch (Exception e) {
			logger.error("删除用户失败", e);
		}
		result.put("flag", flag);
		return result;
	}
}
