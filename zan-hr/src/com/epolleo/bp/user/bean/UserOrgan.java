/*  
 * Copyright (c) 2012 epolleo.com
 * All rights reserved.
 * @author：cf
 * @date：2012-06-28 11:00:44 +0800
 * @version V1.0 
 */
package com.epolleo.bp.user.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 用户组织机构关系bean
 * @author cf
 * @date：2012-06-28 11:12:48 +0800
 */
public class UserOrgan implements Serializable {
    private static final long serialVersionUID = 1L;
    private String userId;
    private Integer defaultOrgId;
    /*
     * 用来作为是否属于该组织机构标示
     */
    private boolean mark = false;
    /**
     * 用来作为是否默认组织机构标示
     */
    private boolean dft = false;
    private List<UserOrgan> children;
    private int orgId;
    private String orgName;
    private String orgType;
    private int orgTypeId;
    private int parentId;
    private String state;

    public int getOrgId() {
        return orgId;
    }

    public String getState() {
        if (children == null){
            return "open";
        } else {
            return "closed";
        }
    }
    public void setState(String state) {
        this.state = state;
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public int getOrgTypeId() {
        return orgTypeId;
    }

    public void setOrgTypeId(int orgTypeId) {
        this.orgTypeId = orgTypeId;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    private String type;

    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getDefaultOrgId() {
        return defaultOrgId;
    }

    public void setDefaultOrgId(Integer defaultOrgId) {
        this.defaultOrgId = defaultOrgId;
    }

    public boolean isDft() {
        return dft;
    }

    public void setDft(boolean dft) {
        this.dft = dft;
    }

    public List<UserOrgan> getChildren() {
        return children;
    }

    public void setChildren(List<UserOrgan> children) {
        this.children = children;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isMark() {
        return mark;
    }

    public void setMark(boolean mark) {
        this.mark = mark;
    }

}
