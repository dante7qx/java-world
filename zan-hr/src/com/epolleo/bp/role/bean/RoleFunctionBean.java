/*-
 * Copyright (c) 2012 epolleo.com
 * All rights reserved. 
 * RoleAction.java
 * Date: 2012-06-29 15:11:39 +0800 
 */
package com.epolleo.bp.role.bean;

/**
 * 
 * @Package com.epolleo.bp.role.bean
 * @Title: RoleFunctionBean.java
 * @version V1.0
 */
public class RoleFunctionBean {
    private String funcCode;
    private int roleId;

    public String getFuncCode() {
        return funcCode;
    }

    public void setFuncCode(String funcCode) {
        this.funcCode = funcCode;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

}
