package org.dante.springmvc.mybatis.service;

import java.util.List;

import org.dante.springmvc.mybatis.domain.Permission;
import org.dante.springmvc.mybatis.vo.PermissionTree;

public interface PermissionService {
	public List<PermissionTree> findPermissionTree() throws Exception;

	public Permission findPermissionById(Integer id) throws Exception;

	public List<Permission> findPermissionByPid(Integer pid) throws Exception;

	public Permission insertPermission(Permission permission) throws Exception;

	public void updatePermission(Permission permission) throws Exception;

	public void deletePermissionById(Integer id) throws Exception;

	public void deletePermissionByPid(Integer pid) throws Exception;

	public Permission convertToPermission(PermissionTree tree);

}
