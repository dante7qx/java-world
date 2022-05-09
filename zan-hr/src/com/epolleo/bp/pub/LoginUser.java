/*-
 * Copyright 2012 www.openwebx.org
 * All rights reserved.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 */

package com.epolleo.bp.pub;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * LoginUser
 * <p>
 * Date: 2012-05-18,16:22:42 +0800
 * 
 * @version 1.0
 */
public class LoginUser implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -7988781209279073613L;
    private static final ThreadLocal<LoginUser> userHolder = new ThreadLocal<LoginUser>();

    public static final LoginUser getCurrentUser() {
        return userHolder.get();
    }

    public static final void setCurrentUser(LoginUser user) {
        userHolder.set(user);
    }

    String[] roles = {};
    private String userId;
    private String userName;
    private String aliasName;
    private String nickName;
    private String phone;
    private String mobile;
    private String address;
    private String email;
    private int state;
    private Integer employeeId; // 对应员工
    private Integer organizationId;
    private boolean sysUser;
    private Map<String, String> functions;
    private Map<Integer, String> organizations;
    private List<String> orgCodes;

    /**
     * 创建匿名用户。
     */
    public LoginUser() {
    }

    public String getUserId() {
        return userId;
    }

    public boolean isSysUser() {
        return sysUser;
    }

    public void setSysUser(boolean sysUser) {
        this.sysUser = sysUser;
    }

    public void setUserId(String name) {
        this.userId = name;
    }

    public String[] getRoles() {
        return roles;
    }

    public String getUserName() {
        return userName;
    }

    public String getAliasName() {
        return aliasName;
    }

    public String getNickName() {
        return nickName;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public int getState() {
        return state;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public Integer getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Integer orgId) {
        this.organizationId = orgId;
    }

    public Map<String, String> getFunctions() {
        return functions;
    }

    public void setFunctions(Map<String, String> functions) {
        this.functions = functions;
    }

    public Map<Integer, String> getOrganizations() {
        return organizations;
    }

    public void setOrganizations(Map<Integer, String> organizations) {
        this.organizations = organizations;
    }
    
    public List<String> getOrgCodes() {
        return this.orgCodes;
    }

    public void setOrgCodes(List<String> orgCodes) {
        this.orgCodes = orgCodes;
    }

}
