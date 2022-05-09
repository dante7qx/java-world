package org.dante.springmvc.mybatis.dao;

import java.util.List;

import org.dante.springmvc.mybatis.domain.Menu;

public interface MenuDao {
	public Menu findMenuById(Integer id) throws Exception;

	public List<Menu> findMenuByPid(Integer pid) throws Exception;

	public void insertMenu(Menu permission) throws Exception;

	public void updateMenu(Menu permission) throws Exception;
}
