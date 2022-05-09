package org.dante.springmvc.mybatis.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.dante.springmvc.mybatis.dao.MenuDao;
import org.dante.springmvc.mybatis.domain.Menu;
import org.dante.springmvc.mybatis.service.MenuService;
import org.dante.springmvc.mybatis.vo.MenuTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class MenuServiceImpl implements MenuService {
	
	@Autowired
	private MenuDao menuDao;

	@Override
	public Menu findMenuById(Integer id) throws Exception {
		return menuDao.findMenuById(id);
	}

	@Override
	public List<Menu> findMenuByPid(Integer pid) throws Exception {
		return menuDao.findMenuByPid(pid);
	}

	@Override
	@Transactional
	public Menu insertMenu(Menu menu) throws Exception {
		menuDao.insertMenu(menu);
		return menu;
	}
	
	@Override
	@Transactional
	public void updateMenu(Menu menu) throws Exception {
		menuDao.updateMenu(menu);
	}
	
	/**
	 * 获取权限树
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<MenuTree> findMenuTree() throws Exception {
		List<Menu> menus = this.findMenuByPid(-1);
		if(menus == null) {
			return null;
		}
		List<MenuTree> trees = new ArrayList<MenuTree>();
		for (Menu menu : menus) {
			MenuTree tree = this.convertToTree(menu);
			trees.add(tree);
			this.buildTree(tree, menu.getId());
		}
		return trees;
	}
	
	private void buildTree(MenuTree tree, Integer pid) throws Exception {
		List<Menu> childs = this.findMenuByPid(pid);
		if(childs != null && !childs.isEmpty()) {
			for (Menu child : childs) {
				MenuTree childTree = this.convertToTree(child);
				tree.getChildren().add(childTree);
				this.buildTree(childTree, child.getId());
			}
		}
		
	}

	private MenuTree convertToTree(Menu menu) {
		MenuTree tree = new MenuTree();
		tree.setId(menu.getId());
		tree.setPid(menu.getPid());
		tree.setName(menu.getName());
		tree.setPathUrl(menu.getPathUrl());
		tree.setPermissionId(menu.getPermissionId());
		tree.setShowOrder(menu.getShowOrder());
		tree.setOpen(true);
		return tree;
	}
	
	public Menu convertToMenu(MenuTree tree) {
		Menu menu = new Menu();
		menu.setId(tree.getId());
		menu.setPid(tree.getPid());
		menu.setName(tree.getName());
		menu.setPathUrl(tree.getPathUrl());
		menu.setPermissionId(tree.getPermissionId());
		menu.setShowOrder(tree.getShowOrder());
		return menu;
	}

}
