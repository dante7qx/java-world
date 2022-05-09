/*-
 * Copyright (c) 2012 epolleo.com
 * All rights reserved. 
 * RoleAction.java
 * Date: 2012-06-29 15:43:54 +0800   
 */
package com.epolleo.bp.role.dao.ibatis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.epolleo.bp.pub.AbstractDao;
import com.epolleo.bp.pub.PagingForm;
import com.epolleo.bp.pub.PagingResult;
import com.epolleo.bp.role.bean.FunctionRole;
import com.epolleo.bp.role.bean.RoleBean;
import com.epolleo.bp.role.bean.RoleNodeBean;

/**
 * RoleDao.java
 */
@SuppressWarnings("unchecked")
public class RoleDao extends AbstractDao<RoleBean> {

    public PagingResult<RoleBean> findPaging(PagingForm pagingParam) {
        String sort = pagingParam.getSort();
        String order = pagingParam.getOrder();
        if (sort != null) {
            checkName(sort);
        }
        if (order != null) {
            checkName(order);
        }
        HashMap<String, Object> params = new HashMap<String, Object>();

        params.put("sort", pagingParam.getSort());
        params.put("order", pagingParam.getOrder());
        params.putAll(pagingParam.getFilter());
        ArrayList<RoleBean> page = new ArrayList<RoleBean>();
        int total = getSqlMapClientTemplate().queryForPage(page,
            "selectPageRole", params, pagingParam.getSkip(),
            pagingParam.getPageSize());
        PagingResult<RoleBean> results = new PagingResult<RoleBean>(page, total);
        return results;
    }

    public RoleBean findRoleBeanById(int id) {
        RoleBean roleBean = (RoleBean) getSqlMapClientTemplate()
            .queryForObject("selectOnlyRole", id);
        return roleBean;
    }

    public void updateRoleBean(RoleBean roleBean) {
        getSqlMapClientTemplate().update("updateRole", roleBean);
    }

    public void saveRoleBean(RoleBean roleBean) {
        getSqlMapClientTemplate().insert("insertRole", roleBean);
    }

    public List<FunctionRole> findRoleAndFunction(int roleId) {
        List<FunctionRole> lists = (List<FunctionRole>) getSqlMapClientTemplate()
            .queryForList("selectFunctionAndRole", roleId);
        return lists;
    }

    public void deleteRoleBean(int id) {
        getSqlMapClientTemplate().delete("deleteRole", id);
    }

    public List<RoleNodeBean> getAllRole() {
        return (List<RoleNodeBean>) getSqlMapClientTemplate().queryForList(
            "getAllRole");
    }
}
