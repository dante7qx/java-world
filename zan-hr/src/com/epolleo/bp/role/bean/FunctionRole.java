/*-
 * Copyright (c) 2012 epolleo.com
 * All rights reserved. 
 * RoleAction.java
 * Date: @date 2012-06-29 15:06:28 +0800 
 */
package com.epolleo.bp.role.bean;

/**
 * 
 * 功能的bean对象
 * 
 * @version V1.0
 */
public class FunctionRole {
    private String funcCode;
    private String funcName;
    private String funcNote;
    private String parent;
    private int funcOrder;
    private int roleId;
    // 通过判断roleId是否为零，决定该角色是否有该功能
    private boolean selected = false;

    public String getFuncCode() {
        return funcCode;
    }

    public void setFuncCode(String funcCode) {
        this.funcCode = funcCode;
    }

    public String getFuncName() {
        return funcName;
    }

    public void setFuncName(String funcName) {
        this.funcName = funcName;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public boolean isSelected() {
        if (this.roleId == 0) {
            return false;
        } else {
            return true;
        }
    }

    public String getFuncNote() {
        return funcNote;
    }

    public void setFuncNote(String funcNote) {
        this.funcNote = funcNote;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public int getFuncOrder() {
        return funcOrder;
    }

    public void setFuncOrder(int funcOrder) {
        this.funcOrder = funcOrder;
    }

}
