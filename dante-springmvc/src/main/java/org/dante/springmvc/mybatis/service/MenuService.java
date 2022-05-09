package org.dante.springmvc.mybatis.service;

import java.util.List;

import org.dante.springmvc.mybatis.domain.Menu;
import org.dante.springmvc.mybatis.vo.MenuTree;

public interface MenuService {
	public List<MenuTree> findMenuTree() throws Exception;
	
	public Menu findMenuById(Integer id) throws Exception;
	
	public List<Menu> findMenuByPid(Integer pid) throws Exception;
	
	public Menu insertMenu(Menu menu) throws Exception;
	
	public void updateMenu(Menu menu) throws Exception;
	
	public Menu convertToMenu(MenuTree tree);
	
}
