/*-
 * Copyright (c) 2012 epolleo.com
 * All rights reserved. 
 * RoleAction.java
 * Date: 2012-06-29 15:29:52 +0800 
 */
package com.epolleo.bp.role.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import com.epolleo.bp.pub.PagingForm;
import com.epolleo.bp.pub.PagingResult;
import com.epolleo.bp.role.bean.FunctionRole;
import com.epolleo.bp.role.bean.FunctionRoleTree;
import com.epolleo.bp.role.bean.RoleBean;
import com.epolleo.bp.role.bean.RoleNodeBean;
import com.epolleo.bp.role.dao.ibatis.RoleDao;

/**
 * 角色的service层
 */
public class RoleService {

    @Resource
    private RoleDao roleDao;

    public RoleService() {
    }
    
    public PagingResult<RoleBean> findPaging(PagingForm pagingParam) {
        return roleDao.findPaging(pagingParam);
    }

    public RoleDao getRoleDao() {
        return roleDao;
    }

    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    public RoleBean findRoleBeanById(int id) {
        return roleDao.findRoleBeanById(id);
    }

    public void updateRoleBean(RoleBean roleBean) {
        roleDao.updateRoleBean(roleBean);
    }

    public void saveRoleBean(RoleBean roleBean) {
        roleDao.saveRoleBean(roleBean);
    }

    public List<FunctionRoleTree> findRoleAndFunction(int roleId) {
        List<FunctionRole> frs = roleDao.findRoleAndFunction(roleId);
        List<FunctionRoleTree> tree = new ArrayList<FunctionRoleTree>();
        Map<String, FunctionRoleTree> map = new LinkedHashMap<String, FunctionRoleTree>();
        for (FunctionRole fr : frs) {
            FunctionRoleTree frt = new FunctionRoleTree();
            frt.setBasicInfo(fr);
            map.put(fr.getFuncCode(), frt);
        }
        Set<String> keySet = map.keySet();
        Iterator it = keySet.iterator();
        while (it.hasNext()) {
            String key = (String) it.next();
            FunctionRoleTree temp = (FunctionRoleTree) map.get(key);
            String parentCode = temp.getParentId();
            if (parentCode.equals("-1")) {
                tree.add(temp);
            } else {
                Set<String> keySet1 = map.keySet();
                Iterator it1 = keySet1.iterator();
                while (it1.hasNext()) {
                    String key1 = (String) it1.next();
                    FunctionRoleTree f = (FunctionRoleTree) map.get(key1);
                    if (parentCode.equals(key1)) {
                        if (f.getChildren() == null) {
                            List<FunctionRoleTree> lists = new ArrayList<FunctionRoleTree>();
                            lists.add(temp);
                            f.setChildren(lists);
                        } else {
                            f.getChildren().add(temp);
                        }
                    }
                }
            }
        }
        return tree;
    }

    public void deleteRoleBean(int id) {
        roleDao.deleteRoleBean(id);

    }

    public List<RoleNodeBean> getAllRole() {
        return roleDao.getAllRole();
    }
}
