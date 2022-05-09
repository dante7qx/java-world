/*  
 * Copyright (c) 2012 epolleo.com
 * All rights reserved.
 * @author：Dante
 * @date：2012-08-03 16:19:44 +0800
 * @version V1.0 
 */
package com.epolleo.bp.employee.bean;

import java.util.ArrayList;
import java.util.List;

import com.epolleo.bp.pub.BaseBean;
import com.epolleo.bp.user.bean.LoginUser;

/**
 * 员工bean
 * 
 * @author Dante
 * @date：2012-08-03 16:19:44 +0800
 */
public class Employee extends BaseBean {
    private static final long serialVersionUID = 1L;
    private Integer employeeId;
    private String employeeName;
    private String pyCode;
    private String aliasName;
    private String nickName;
    private String passWord;
    private String phone;
    private String mobileNumber;
    private String address;
    private String email;
    private int state;
    private String isUserId; // 用来判断用户ID是否被删除

    private List<LoginUser> userList; // 员工的所用帐户信息
    private Integer[] orgIds = new Integer[0];
    private String orgNames;

    // 组织机构信息
    private int defaultOrgId;

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

    public String getPyCode() {
        return pyCode;
    }

    public void setPyCode(String pyCode) {
        this.pyCode = pyCode;
    }

    public String getAliasName() {
        return aliasName;
    }

    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getIsUserId() {
        return isUserId;
    }

    public void setIsUserId(String isUserId) {
        this.isUserId = isUserId;
    }

    public int getDefaultOrgId() {
        return defaultOrgId;
    }

    public void setDefaultOrgId(int defaultOrgId) {
        this.defaultOrgId = defaultOrgId;
    }

    public List<LoginUser> getUserList() {
        if (userList == null) {
            userList = new ArrayList<LoginUser>();
        }
        return userList;
    }

    public Integer[] getOrgIds() {
        return orgIds;
    }

    public void setOrgIds(Integer[] orgIds) {
        this.orgIds = orgIds;
    }

    public String getOrgNames() {
        return orgNames;
    }

    public void setOrgNames(String orgNames) {
        this.orgNames = orgNames;
    }

}
