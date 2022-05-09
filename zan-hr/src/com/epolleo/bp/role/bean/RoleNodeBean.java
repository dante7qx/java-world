/*-
 * Copyright 2012 Owl Group
 * All rights reserved.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 */

package com.epolleo.bp.role.bean;

import java.util.List;

/**
 * RoleNodeBean
 * <p>
 * Date: 2012-12-21,12:02:08 +0800
 * 
 * @author Song Sun
 * @version 1.0
 */
public class RoleNodeBean {
    int id;
    String text;
    List<RoleNodeBean> children;
    String state;

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setId(int roleId) {
        this.id = roleId;
    }

    public void setText(String roleName) {
        this.text = roleName;
    }

    public List<RoleNodeBean> getChildren() {
        return children;
    }

    public void setChildren(List<RoleNodeBean> children) {
        this.children = children;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

}
