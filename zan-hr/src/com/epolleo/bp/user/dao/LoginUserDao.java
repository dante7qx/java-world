/*  
 * Copyright (c) 2012 epolleo.com
 * All rights reserved.
 * @author：cf
 * @date：2012-06-28 11:00:44 +0800
 * @version V1.0 
 */
package com.epolleo.bp.user.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.epolleo.bp.pub.AbstractDao;
import com.epolleo.bp.pub.PagingForm;
import com.epolleo.bp.pub.PagingResult;
import com.epolleo.bp.user.bean.LoginUser;
import com.epolleo.bp.user.bean.UserOrgan;
import com.epolleo.bp.user.bean.UserRole;

@SuppressWarnings("unchecked")
public class LoginUserDao extends AbstractDao {

    public PagingResult<LoginUser> findPaging(PagingForm pagingParam) {
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
        ArrayList<LoginUser> page = new ArrayList<LoginUser>();
        int total = getSqlMapClientTemplate().queryForPage(page, "selectUser",
            params, pagingParam.getSkip(), pagingParam.getPageSize());
        PagingResult<LoginUser> result = new PagingResult<LoginUser>(page,
            total);
        return result;
    }
    
    public PagingResult<LoginUser> findRoleAccountPaging(PagingForm pagingParam) {
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
        ArrayList<LoginUser> page = new ArrayList<LoginUser>();
        int total = getSqlMapClientTemplate().queryForPage(page, "selectRoleUser",
            params, pagingParam.getSkip(), pagingParam.getPageSize());
        PagingResult<LoginUser> result = new PagingResult<LoginUser>(page,
            total);
        return result;
    }

    public int update(LoginUser userGen) {
        getSqlMapClientTemplate().update("updateUser", userGen, 1);
        return 1;
    }

    public int updateUserUpdateInfo(LoginUser userGen) {
        getSqlMapClientTemplate().update("updateUserUpdateInfo", userGen, 1);
        return 1;
    }

    /**
     * 保存用户基本信息
     */
    public LoginUser save(LoginUser usr) {
        getSqlMapClientTemplate().insert("insertUser", usr);
        return usr;
    }

    public int delete(String id) {
        getSqlMapClientTemplate().delete("deleteUser", id, 1);
        return 1;
    }

    public int deleteByEmployeeId(Integer id) {
        int delete = getSqlMapClientTemplate().delete("deleteUserByEmployeeId",
            id);
        return delete;
    }

    public List<UserRole> getUserRole(String userId) {
        List<UserRole> records = (List<UserRole>) getSqlMapClientTemplate()
            .queryForList("selectUserRole", userId);
        return records;
    }

    public void deleteUserRoles(String userId) {
        getSqlMapClientTemplate().delete("deleteUserRoles", userId);
    }

    public int insertUserRole(UserRole userRole) {
        return getSqlMapClientTemplate().update("insertUserRole", userRole);
    }

    public int queryUR(UserOrgan org) {
        int total = (Integer) getSqlMapClientTemplate().queryForObject(
            "selectUserOrgCount", org);
        return total;
    }

    /**
     * 获取用户与组织机构关系
     * 
     * @param userId
     * @return
     */
    public List<UserOrgan> getUserOrgan(String userId) {
        List<UserOrgan> result = null;
        if (StringUtils.isNotBlank(userId)) {
            result = getSqlMapClientTemplate().queryForList("selectUserOrgan",
                userId);
        } else {
            result = getSqlMapClientTemplate()
                .queryForList("selectOrganNoUser");
        }
        return result;
    }

    public int updateUserOrg(UserOrgan userOrg) {
        if (userOrg.isMark()) { // 由选中变成未选中
            getSqlMapClientTemplate().delete("deleteUserOrg", userOrg);
        } else {
            getSqlMapClientTemplate().insert("insertUserOrg", userOrg);
        }
        return 1;
    }

    public void deleteUserOrg(UserOrgan userOrg) {
        getSqlMapClientTemplate().delete("deleteUserOrg", userOrg);

    }

    public void insertUserOrg(UserOrgan userOrg) {
        getSqlMapClientTemplate().insert("insertUserOrg", userOrg);

    }

    public int updateUserOrgDft(LoginUser user) {
        getSqlMapClientTemplate().update("updateUserDefaultOrg", user);
        return 1;
    }

    public LoginUser getUser(String userId) {
        return (LoginUser) getSqlMapClientTemplate().queryForObject("getUser",
            userId);
    }

    public Map<Integer, String> getOrgByUserId(String userId) {
        return getSqlMapClientTemplate().queryForMap("getOrgByUserId", userId,
            "ORG_ID", "ORG_NAME");
    }

    public List<UserOrgan> queryOrgByUserId(String userId) {
        return getSqlMapClientTemplate().queryForList("queryOrgByUserId",
            userId);
    }

    public LoginUser getUserByEmployeeId(Integer employeeId) {
        return (LoginUser) getSqlMapClientTemplate().queryForObject(
            "getUserByEmployeeId", employeeId);
    }

    public boolean isResCurrentUser(String userId, String funId) {
        Integer count = (Integer) this.getSqlMapClientTemplate()
            .queryForObjectArgs("isResByFunId", userId, funId);
        if (count != 0) {
            return true;
        } else {
            return false;
        }
    }

    public String queryMobileById(String userId) {
        return (String) getSqlMapClientTemplate().queryForFirstArgs(
            "queryMobileById", userId);
    }

    public List<LoginUser> queryByRoleId(int roleId) {
        return (List<LoginUser>) getSqlMapClientTemplate().queryForListArgs(
            "queryUserByRoleId", roleId);
    }

    public boolean isExistUserRole(int roleId) {
        Integer count = (Integer) getSqlMapClientTemplate().queryForObject(
            "isExistUserRole", roleId);
        return count > 0 ? true : false;
    }

    public List<Integer> getUserRoleIdList(String userId) {
        return (List<Integer>) getSqlMapClientTemplate().queryForList(
            "getUserRoleIdList", userId);
    }

}
