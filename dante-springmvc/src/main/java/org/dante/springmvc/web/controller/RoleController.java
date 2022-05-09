package org.dante.springmvc.web.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dante.springmvc.mybatis.domain.Role;
import org.dante.springmvc.mybatis.domain.RolePermission;
import org.dante.springmvc.mybatis.service.RoleService;
import org.dante.springmvc.spring.exception.DanteException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/role")
public class RoleController {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private RoleService roleService;

	@RequestMapping(value = "/querytree", method = RequestMethod.POST, produces = "application/json")
	public List<Role> findRoles(Role role) throws DanteException {
		List<Role> roles = null;
		try {
			Map<String, Object> filterMap = new HashMap<String, Object>();
			if (!StringUtils.isEmpty(role.getName())) {
				filterMap.put("name", "%" + role.getName() + "%");
			}
			roles = roleService.findRoles(filterMap);
		} catch (Exception e) {
			logger.error("获取角色失败", e);
		}
		return roles;
	}
	
	@RequestMapping(value = "/queryById", method = RequestMethod.POST, produces = "application/json")
	public Role findById(Integer id) {
		Role role = null;
		try {
			role = roleService.findRoleById(id);
			List<RolePermission> rolePermissions = roleService.selectRolePermissionByRoleId(id);
			if(rolePermissions != null && !rolePermissions.isEmpty()) {
				Set<Integer> permissionIds = new HashSet<Integer>();
				for (RolePermission rolePermission : rolePermissions) {
					permissionIds.add(rolePermission.getPermissionId());
				}
				role.setPermissionIds(permissionIds);
			}
		} catch (Exception e) {
			logger.error("获取Role失败"+id, e);
		}
		return role;
	}

	@RequestMapping(value = "/updatetree", method = RequestMethod.POST, produces = "application/json")
	public Map<String, Object> updateRole(Role role) {
		Map<String, Object> result = new HashMap<String, Object>();
		boolean flag = false;
		try {
			if (role.getId() == null) {
				roleService.insertRole(role);
			} else {
				roleService.updateRole(role);
			}
			flag = true;
		} catch (Exception e) {
			logger.error("更新角色失败", e);
		}
		result.put("data", role);
		result.put("flag", flag);
		return result;
	}

	@RequestMapping(value = "/deleteById", method = RequestMethod.POST, produces = "application/json")
	public Map<String, Object> deleteById(Integer id) {
		Map<String, Object> result = new HashMap<String, Object>();
		boolean flag = false;
		try {
			roleService.deleteRoleById(id);
			flag = true;
		} catch (Exception e) {
			logger.error("删除角色失败", e);
		}
		result.put("flag", flag);
		return result;
	}
}
