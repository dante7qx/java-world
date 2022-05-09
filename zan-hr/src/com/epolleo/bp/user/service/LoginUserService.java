/*  
 * Copyright (c) 2012 epolleo.com
 * All rights reserved.
 *
 * 描述：<描述信息>
 * @author：cf
 * @date：2012-06-28 11:00:44 +0800
 * @version V1.0 
 */
package com.epolleo.bp.user.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import com.epolleo.bp.pub.PagingForm;
import com.epolleo.bp.pub.PagingResult;
import com.epolleo.bp.user.bean.LoginUser;
import com.epolleo.bp.user.bean.UserRole;
import com.epolleo.bp.user.dao.LoginUserDao;

/**
 * 用户管理service
 * 
 * @author cf
 * @date 2012-06-2810:48:40 +0800
 */
public class LoginUserService {

    @Resource
    private LoginUserDao loginUserDao;

    public PagingResult<LoginUser> findPaging(PagingForm pagingParam) {
        return loginUserDao.findPaging(pagingParam);
    }
    
    public PagingResult<LoginUser> findRoleAccountPaging(PagingForm pagingParam) {
        return loginUserDao.findRoleAccountPaging(pagingParam);
    }

    public LoginUser save(LoginUser userGen, Set<Integer> roleList) {
        LoginUser usr = loginUserDao.save(userGen);
        loginUserDao.deleteUserRoles(usr.getUserId());
        for (Integer rid : roleList) {
            UserRole userRole = new UserRole();
            userRole.setUserId(usr.getUserId());
            userRole.setId(rid);
            loginUserDao.insertUserRole(userRole);
        }
        return usr;
    }

    public int delete(String idStr) {
        return loginUserDao.delete(idStr);
    }

    public int deleteByEmployeeId(Integer id) {
        return loginUserDao.deleteByEmployeeId(id);
    }

    public int update(LoginUser usr, Set<Integer> roleList) {
        loginUserDao.deleteUserRoles(usr.getUserId());
        for (Integer rid : roleList) {
            UserRole userRole = new UserRole();
            userRole.setUserId(usr.getUserId());
            userRole.setId(rid);
            loginUserDao.insertUserRole(userRole);
        }
        return loginUserDao.update(usr);
    }

    public LoginUser getLoginUser(String userId) {
        return loginUserDao.getUser(userId);
    }

    public LoginUser getUserByEmployeeId(Integer employeeId) {
        return loginUserDao.getUserByEmployeeId(employeeId);
    }

    public List<UserRole> getUserRole(String userId) {
        List<UserRole> result = new ArrayList<UserRole>();
        List<UserRole> list = loginUserDao.getUserRole(userId);
        for (UserRole userRole : list) {
            if (userRole.getUserId() != null) {
                userRole.setChecked(true);
            } else {
                userRole.setChecked(false);
            }
            result.add(userRole);
        }
        return result;
    }

    public int updateUserUpdateInfo(LoginUser userGen) {
        return loginUserDao.updateUserUpdateInfo(userGen);
    }

    /**
     * 判断登录用户是否有此权限
     */
    public boolean isResCurrentUser(String userId, String funId) {
        return loginUserDao.isResCurrentUser(userId, funId);
    }

    public String queryMobileById(String userId) {
        return loginUserDao.queryMobileById(userId);
    }

    public List<LoginUser> queryByRoleId(int roleId) {
        return loginUserDao.queryByRoleId(roleId);
    }

    public boolean isExistUserRole(int roleId) {
        return loginUserDao.isExistUserRole(roleId);
    }

    public List<Integer> getUserRoleIdList(String userId) {
        return loginUserDao.getUserRoleIdList(userId);
    }
}
