package org.dante.springmvc.mybatis.dao;

import java.util.List;

import org.dante.springmvc.mybatis.domain.Permission;

public interface PermissionDao {
	
	public Permission findPermissionById(Integer id) throws Exception;
	
	public List<Permission> findPermissionByPid(Integer pid) throws Exception;
	
	public void insertPermission(Permission permission) throws Exception;
	
	public void updatePermission(Permission permission) throws Exception;
	
	public void deletePermissionById(Integer id) throws Exception;
	
	public void deletePermissionByPid(Integer pid) throws Exception;
}
