/*-
 * Copyright (c) 2012 epolleo.com
 * All rights reserved. 
 * RoleAction.java
 * Date: 2012-06-29 15:30:47 +0800  
 */
package com.epolleo.bp.role.service;

import java.util.Set;

import javax.annotation.Resource;

import com.epolleo.bp.role.dao.ibatis.RoleFunctionDao;

/**
 * 角色功能的service层
 */
public class RoleFunctionService {
	@Resource
	private RoleFunctionDao roleFunctionDao;

	public void updateRoleFunction(int roleId, Set<String> fset) {
		roleFunctionDao.deleteRoleFunctionByRoleId(roleId);
        roleFunctionDao.insertRoleFunctions(roleId, fset);
	}

	public void deleteRoleFunctionByRoleId(int roleId) {
		roleFunctionDao.deleteRoleFunctionByRoleId(roleId);
	}
}
