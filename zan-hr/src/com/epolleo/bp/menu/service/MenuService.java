/*  
 * Copyright (c) 2012 epolleo.com
 * All rights reserved.
 * @author：cf
 * @date：2012-06-28 11:00:44 +0800
 * @version V1.0 
 */
package com.epolleo.bp.menu.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.citrus.service.configuration.ProductionModeAware;
import com.epolleo.bp.menu.bean.Menu;
import com.epolleo.bp.menu.bean.MenuTree;
import com.epolleo.bp.menu.bean.Win8Menu;
import com.epolleo.bp.menu.dao.MenuDao;

public class MenuService implements ProductionModeAware {
    private Logger logger = LoggerFactory.getLogger(MenuService.class);
    @Resource
    private MenuDao menuDao;

    @Resource
    private Integer menuRootId = -1;

    @Resource
    private Boolean allowLeafAt1Level = true;

    private boolean productionMode;

    public void setProductionMode(boolean productionMode) {
        this.productionMode = productionMode;
    }

    /**
     * 获取menuId或ParentId 不为id的树形
     */
    public List<MenuTree> getMenuNotId(int id) {
        List<MenuTree> result = new ArrayList<MenuTree>();
        List<Menu> list = menuDao.getMenuNotId(id);
        MenuTree root = new MenuTree();
        root.setId(-1);
        root.setText("所有菜单");
        try {
            result.add(initMenuTree(root, list));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * 把数据库菜单信息构造成树形结构过程
     */
    MenuTree initMenuTree(MenuTree root, List<Menu> list) {
        Map<Integer, MenuTree> map = new LinkedHashMap<Integer, MenuTree>();
        for (Menu menu : list) {
            MenuTree m = new MenuTree();
            m.setChildren(null);
            m.setId(menu.getMenuId());
            m.setText(menu.getName());
            m.setAttributes(menu);
            map.put(m.getId(), m);
        }
        Set<Integer> keySet = map.keySet();
        for (Integer key : keySet) {
            MenuTree menu = map.get(key);
            int prentId = menu.getAttributes().getParentId();
            if (prentId == -1) { // 根节点
                if (root.getChildren() == null) {
                    List<MenuTree> l = new ArrayList<MenuTree>();
                    l.add(menu);
                    root.setChildren(l);
                } else {
                    root.getChildren().add(menu);
                }
            } else {
                for (Integer k : keySet) {
                    MenuTree m = map.get(k);
                    if (k == prentId) {
                        if (m.getChildren() == null) {
                            List<MenuTree> l = new ArrayList<MenuTree>();
                            l.add(menu);
                            m.setChildren(l);
                        } else {
                            m.getChildren().add(menu);
                        }
                    }
                }
            }
        }
        return root;
    }

    public Menu doSave(Menu menu) {
        return menuDao.doSave(menu);
    }

    public int doUpdate(Menu menu) {
        return menuDao.doUpdate(menu);
    }

    public int doDelete(int id) {
        return menuDao.doDelete(id);
    }

    public int updateParentId(int id, int parentId) {
        return menuDao.updateParentId(id, parentId);
    }

    public List<Menu> getChild(int parentId) {
        return menuDao.getChild(parentId);
    }

    MenuTree initUserMenuTree(MenuTree root, Map<Integer, MenuTree> map,
        List<Integer> dirs) {
        Set<Integer> keySet = map.keySet();
        for (Integer key : keySet) {
            MenuTree menu = map.get(key);
            int prentId = menu.getAttributes().getParentId();
            if (prentId == -1) { // 根节点
                root.addChild(menu);
            } else {
                MenuTree m = map.get(prentId);
                if (m != null) {
                    m.addChild(menu);
                } else {
                    logger.warn("Orphan menu: " + m);
                }
            }
        }
        for (Integer dir : dirs) {
            removeEmptyMenu(dir, map);
        }

        return root;
    }

    void removeEmptyMenu(Integer dir, Map<Integer, MenuTree> map) {
        if (dir.equals(menuRootId)) {
            return;
        }
        MenuTree m = map.get(dir);
        if (m == null) {
            return;
        }
        if (m.hasChild()) {
            return;
        }
        Integer pid = m.getAttributes().getParentId();
        MenuTree p = map.get(pid);
        if (p != null) {
            p.getChildren().remove(m);
            removeEmptyMenu(p.getId(), map);
        }
    }

    private MenuTree toMenuTree(Menu menu) {
        MenuTree m = new MenuTree();
        m.setId(menu.getMenuId());
        m.setText(menu.getName());
        m.setAttributes(menu);

        return m;
    }

	public List<Win8Menu> getWin8MenuForUser(String userId) {
		List<Win8Menu> list = menuDao.getWin8Menu(productionMode, userId);
		return list;
	}

    public List<MenuTree> getMenuForUser(String userId, String showIn) {
        List<Integer> dirs = menuDao.getMenuDir();
        List<Menu> list = menuDao.getMenu(productionMode, userId, true, showIn);
        Map<Integer, MenuTree> mtree = new LinkedHashMap<Integer, MenuTree>();
        for (Menu m : list) {
            mtree.put(m.getMenuId(), toMenuTree(m));
        }

        MenuTree root = new MenuTree();
        root.setChildren(new ArrayList<MenuTree>());
        root.setId(menuRootId);
        root.setText("所有菜单");
        try {
            initUserMenuTree(root, mtree, dirs);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        List<MenuTree> all = new ArrayList<MenuTree>();

        for (MenuTree m : root.getChildren()) {
            if (m.hasChild()
                || (Boolean.TRUE == allowLeafAt1Level && !dirs
                    .contains(m.getId()))) {
                all.add(m);
            }
        }
        return all;
    }

    public List<MenuTree> getMenuAll() {
        List<MenuTree> result = new ArrayList<MenuTree>();
        List<Menu> list = menuDao.getMenu(productionMode, null, false, null);
        MenuTree root = new MenuTree();
        root.setId(menuRootId);
        root.setText("所有菜单");
        try {
            initMenuTree(root, list);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        result.add(root);
        root.setId(-1);
        return result;
    }

}
