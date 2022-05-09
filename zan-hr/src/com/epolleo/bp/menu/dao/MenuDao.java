/*  
 * Copyright (c) 2012 epolleo.com
 * All rights reserved.
 * @author：cf
 * @date：2012-06-28 11:00:44 +0800
 * @version V1.0 
 */
package com.epolleo.bp.menu.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.epolleo.bp.menu.bean.Menu;
import com.epolleo.bp.menu.bean.Win8Menu;
import com.epolleo.bp.pub.AbstractDao;

/**
 * 菜单dao类
 * 
 * @author cf
 * @date：2012-07-09 18:16:24 +0800
 */
@SuppressWarnings("unchecked")
public class MenuDao extends AbstractDao {

    /**
     * 获取所有菜单
     * 
     * @date：2012-06-28 11:26:45 +0800
     */
    public List<Menu> getMenu(boolean productMode, String userId,
        boolean isInitMenu, String showIn) {
        List<Menu> menu = null;
        if (isInitMenu) {
    		HashMap<String, Object> params = new HashMap<String, Object>();
    		if(!StringUtils.isEmpty(showIn)){
            	params.put("showIn", showIn);
    		}
            if ("superadmin".equals(userId) || !productMode) {
                menu = getSqlMapClientTemplate().queryForList("selectMenu", params);
            } else {
            	params.put("userId", userId);
                menu = getSqlMapClientTemplate().queryForList(
                    "queryMenuByUserId", params);
            }
        } else {
            menu = getSqlMapClientTemplate().queryForList("selectMenu");
        }

        return menu;
    }
    
    public List<Win8Menu> getWin8Menu(boolean productMode, String userId) {
    	List<Win8Menu> menu = null;
    	if ("superadmin".equals(userId) || !productMode) {
			menu = getSqlMapClientTemplate().queryForList("selectWin8Menu");
		} else {
			menu = getSqlMapClientTemplate().queryForList(
					"selectWin8MenuByUserId", userId);
		}
    	return menu;
    }

    public Menu doSave(Menu menu) {
        // insertMenu
        Menu m = (Menu) getSqlMapClientTemplate().insert("insertMenu", menu);
        return m;
    }

    public int doUpdate(Menu menu) {
        getSqlMapClientTemplate().update("updateMenu", menu);
        return 1;
    }

    public int doDelete(int id) {
        getSqlMapClientTemplate().delete("deleteMenu", id);
        return 1;
    }

    public List<Menu> getMenuNotId(int id) {
        return getSqlMapClientTemplate().queryForList("selectMenuNotId", id);
    }

    public int updateParentId(int id, int parentId) {
        Menu m = new Menu();
        m.setMenuId(id);
        m.setParentId(parentId);
        return getSqlMapClientTemplate().update("updateParentId", m);
    }

    public List<Menu> getChild(int parentId) {
        return getSqlMapClientTemplate().queryForList("selectChild", parentId);
    }

    public List<Integer> getMenuDir() {
        return (List<Integer>) getSqlMapClientTemplate().queryForList(
            "getMenuDir");
    }

    public Map<String, String> getMenuPathTitleMap() {
        return (Map<String, String>) getSqlMapClientTemplate().queryForMap(
            "getMenuPathTitleMap", null, "PATH", "TITLE");
    }
}
