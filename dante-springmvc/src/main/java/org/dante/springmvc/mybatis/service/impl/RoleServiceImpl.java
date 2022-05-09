package org.dante.springmvc.mybatis.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dante.springmvc.mybatis.dao.RoleDao;
import org.dante.springmvc.mybatis.domain.Role;
import org.dante.springmvc.mybatis.domain.RolePermission;
import org.dante.springmvc.mybatis.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleDao roleDao;

	@Override
	public List<Role> findRoles(Map<String, Object> filterMap) throws Exception {
		return roleDao.findRoles(filterMap);
	}

	@Override
	public Role findRoleById(Integer id) throws Exception {
		return roleDao.findRoleById(id);
	}

	@Override
	@Transactional
	public void insertRole(Role role) throws Exception {
		roleDao.insertRole(role);
		Set<Integer> permissionIds = role.getPermissionIds();
		if (permissionIds != null && !permissionIds.isEmpty()) {
			for (Integer permissionId : permissionIds) {
				RolePermission rolePermission = new RolePermission();
				rolePermission.setRoleId(role.getId());
				rolePermission.setPermissionId(permissionId);
				roleDao.insertRolePermission(rolePermission);
			}
		}
	}

	@Override
	@Transactional
	public void updateRole(Role role) throws Exception {
		roleDao.updateRole(role);
		roleDao.deleteRolePermissionByRoleId(role.getId());
		Set<Integer> permissionIds = role.getPermissionIds();
		if (permissionIds != null && !permissionIds.isEmpty()) {
			for (Integer permissionId : permissionIds) {
				RolePermission rolePermission = new RolePermission();
				rolePermission.setRoleId(role.getId());
				rolePermission.setPermissionId(permissionId);
				roleDao.insertRolePermission(rolePermission);
			}
		}
	}

	@Override
	@Transactional
	public void deleteRoleById(Integer id) throws Exception {
		roleDao.deleteRolePermissionByRoleId(id);
		roleDao.deleteUserRoleByRoleId(id);
		roleDao.deleteRoleById(id);
	}

	@Override
	public List<RolePermission> selectRolePermissionByRoleId(Integer roleId) throws Exception {
		return roleDao.selectRolePermissionByRoleId(roleId);
	}

	@Override
	@Transactional
	public void insertRolePermission(RolePermission rolePermission) throws Exception {
		roleDao.insertRolePermission(rolePermission);
	}

	@Override
	@Transactional
	public void deleteRolePermissionByRoleId(Integer roleId) throws Exception {
		roleDao.deleteRolePermissionByRoleId(roleId);
	}

}
