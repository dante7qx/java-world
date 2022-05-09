/*  
 * Copyright (c) 2012 epolleo.com
 * All rights reserved.
 * @author：cf
 * @date：2012-06-28 11:00:44 +0800
 * @version V1.0 
 */
package com.epolleo.bp.user.bean;

import java.util.Date;

import com.epolleo.bp.pub.BaseBean;

/**
 * 用户基本信息bean
 * 
 * @author cf
 * @date：2012-06-28 11:12:13 +0800
 */
public class LoginUser extends BaseBean {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 2465860379351179289L;
    private String id;
    private String userId;
    private String userName;
    private String passWord;
    private Date passwordTime;
    private int state;
    private boolean sysUser;        // 登录用户是否系统用户
    private String isUserId; // 用来判断用户ID是否被删除

    private Integer employeeId; // 对应员工
    private String employeeName; // 员工名称

    private Integer orgId;
    private String orgIds;
    private String orgNames;
    
    public String getIsUserId() {
        return isUserId;
    }

    public void setIsUserId(String isUserId) {
        this.isUserId = isUserId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public boolean isSysUser() {
        return sysUser;
    }

    public void setSysUser(boolean sysUser) {
        this.sysUser = sysUser;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public Date getPasswordTime() {
        return passwordTime;
    }

    public void setPasswordTime(Date passwordTime) {
        this.passwordTime = passwordTime;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }
    
    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getOrgIds() {
        return orgIds;
    }

    public void setOrgIds(String orgIds) {
        this.orgIds = orgIds;
    }

    public String getOrgNames() {
        return orgNames;
    }

    public void setOrgNames(String orgNames) {
        this.orgNames = orgNames;
    }
    
    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    @Override
    public String toString() {
        return "LoginUser [id=" + id + ", userId=" + userId + ", userName="
            + userName + ", password=" + passWord + ", passwordTime="
            + passwordTime + ", state=" + state + ", sysUser=" + sysUser
            + ", employeeId=" + employeeId + "]";
    }

}
