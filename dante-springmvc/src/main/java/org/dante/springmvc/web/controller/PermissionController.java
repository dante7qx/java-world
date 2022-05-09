package org.dante.springmvc.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dante.springmvc.mybatis.domain.Permission;
import org.dante.springmvc.mybatis.service.PermissionService;
import org.dante.springmvc.mybatis.vo.PermissionTree;
import org.dante.springmvc.spring.exception.DanteException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/permission")
public class PermissionController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private PermissionService permissionService;

	@RequestMapping(value = "/querytree", produces = "application/json")
	@ResponseBody
	public List<PermissionTree> findPermissions() throws DanteException {
		List<PermissionTree> permissions = null;
		try {
			permissions = permissionService.findPermissionTree();
		} catch (Exception e) {
			logger.error("获取权限失败", e);
			throw new DanteException("获取权限失败", e);
		}
		return permissions;
	}

	@RequestMapping(value = "/updatetree", produces = "application/json")
	@ResponseBody
	public Map<String, Object> updatePermission(PermissionTree tree) {
		Map<String, Object> result = new HashMap<String, Object>();
		boolean flag = false;
		Permission permission = permissionService.convertToPermission(tree);
		try {
			if (tree.getId() == null) {
				permissionService.insertPermission(permission);
			} else {
				permissionService.updatePermission(permission);
			}
			flag = true;
		} catch (Exception e) {
			logger.error("更新权限失败", e);
		}
		result.put("data", permission);
		result.put("flag", flag);
		return result;
	}

	@RequestMapping(value = "/deleteById", produces = "application/json")
	@ResponseBody
	public Map<String, Object> deleteById(Integer id) {
		Map<String, Object> result = new HashMap<String, Object>();
		boolean flag = false;
		try {
			permissionService.deletePermissionById(id);
			flag = true;
		} catch (Exception e) {
			logger.error("删除权限失败", e);
		}
		result.put("flag", flag);
		return result;
	}

}
