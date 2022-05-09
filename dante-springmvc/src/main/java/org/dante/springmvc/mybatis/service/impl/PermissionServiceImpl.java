package org.dante.springmvc.mybatis.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.dante.springmvc.mybatis.dao.PermissionDao;
import org.dante.springmvc.mybatis.domain.Permission;
import org.dante.springmvc.mybatis.service.PermissionService;
import org.dante.springmvc.mybatis.vo.PermissionTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PermissionServiceImpl implements PermissionService {
	
	@Autowired
	private PermissionDao permissionDao;

	@Override
	public Permission findPermissionById(Integer id) throws Exception {
		return permissionDao.findPermissionById(id);
	}

	@Override
	public List<Permission> findPermissionByPid(Integer pid) throws Exception {
		return permissionDao.findPermissionByPid(pid);
	}

	@Override
	@Transactional
	public Permission insertPermission(Permission permission) throws Exception {
		permissionDao.insertPermission(permission);
		return permission;
	}
	
	@Override
	@Transactional
	public void updatePermission(Permission permission) throws Exception {
		permissionDao.updatePermission(permission);
	}
	
	/**
	 * 获取权限树
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<PermissionTree> findPermissionTree() throws Exception {
		List<Permission> permissions = this.findPermissionByPid(-1);
		if(permissions == null) {
			return null;
		}
		List<PermissionTree> trees = new ArrayList<PermissionTree>();
		for (Permission permission : permissions) {
			PermissionTree tree = this.convertToTree(permission);
			trees.add(tree);
			this.buildTree(tree, permission.getId());
		}
		return trees;
	}
	
	private void buildTree(PermissionTree tree, Integer pid) throws Exception {
		List<Permission> childs = this.findPermissionByPid(pid);
		if(childs != null && !childs.isEmpty()) {
			for (Permission child : childs) {
				PermissionTree childTree = this.convertToTree(child);
				tree.getChildren().add(childTree);
				this.buildTree(childTree, child.getId());
			}
		}
		
	}

	private PermissionTree convertToTree(Permission permission) {
		PermissionTree tree = new PermissionTree();
		tree.setId(permission.getId());
		tree.setPid(permission.getPid());
		tree.setName(permission.getName());
		tree.setCode(permission.getCode());
		tree.setOpen(true);
		return tree;
	}
	
	public Permission convertToPermission(PermissionTree tree) {
		Permission permission = new Permission();
		permission.setId(tree.getId());
		permission.setPid(tree.getPid());
		permission.setName(tree.getName());
		permission.setCode(tree.getCode());
		return permission;
	}

	@Override
	@Transactional
	public void deletePermissionById(Integer id) throws Exception {
		permissionDao.deletePermissionByPid(id);
		permissionDao.deletePermissionById(id);
	}

	@Override
	@Transactional
	public void deletePermissionByPid(Integer pid) throws Exception {
		permissionDao.deletePermissionByPid(pid);
	}

}
