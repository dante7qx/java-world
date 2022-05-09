package org.dante.springmvc.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dante.springmvc.mybatis.domain.Menu;
import org.dante.springmvc.mybatis.service.MenuService;
import org.dante.springmvc.mybatis.vo.MenuTree;
import org.dante.springmvc.spring.exception.DanteException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/menu")
public class MenuController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private MenuService menuService;

	@RequestMapping(value = "/querytree", produces = "application/json;chartset=UTF-8")
	@ResponseBody
	public List<MenuTree> findMenus() throws DanteException {
		List<MenuTree> menus = null;
		try {
			menus = menuService.findMenuTree();
		} catch (Exception e) {
			logger.error("获取菜单失败", e);
			throw new DanteException("获取菜单失败", e);
		}
		return menus;
	}
	
	@RequestMapping(value = "/updatetree", produces = "application/json;chartset=UTF-8")
	@ResponseBody
	public Map<String, Object> updateMenu(MenuTree tree)  throws DanteException {
		Map<String, Object> result = new HashMap<String, Object>();
		boolean flag = false;
		Menu menu = menuService.convertToMenu(tree);
		try {
			if(tree.getId() == null) {
				menuService.insertMenu(menu);
			} else {
				menuService.updateMenu(menu);
			}
			flag = true;
		} catch (Exception e) {
			logger.error("更新菜单失败", e);
			throw new DanteException("更新菜单失败", e);
		}
		result.put("data", menu);
		result.put("flag", flag);
		return result;		
	}

}
