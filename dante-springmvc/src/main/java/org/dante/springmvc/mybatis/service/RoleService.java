package org.dante.springmvc.mybatis.service;

import java.util.List;
import java.util.Map;

import org.dante.springmvc.mybatis.domain.Role;
import org.dante.springmvc.mybatis.domain.RolePermission;

public interface RoleService {
	public List<Role> findRoles(Map<String, Object> filterMap) throws Exception;
	
	public Role findRoleById(Integer id) throws Exception;

	public void insertRole(Role role) throws Exception;

	public void updateRole(Role role) throws Exception;

	public void deleteRoleById(Integer id) throws Exception;
	
	public List<RolePermission> selectRolePermissionByRoleId(Integer roleId) throws Exception;
	
	public void insertRolePermission(RolePermission rolePermission) throws Exception;
	
	public void deleteRolePermissionByRoleId(Integer roleId) throws Exception;
}
