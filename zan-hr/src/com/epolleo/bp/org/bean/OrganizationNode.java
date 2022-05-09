/**
 * Copyright (c) 2012 epolleo.com
 * All rights reserved. 
 * OrgTree.java
 * Date: 2012-6-29
 */
package com.epolleo.bp.org.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 组织机构树
 * </p>
 */
public class OrganizationNode {

    private OrgNodeBean bean;
    private Boolean state;

    private List<OrganizationNode> children;

    public String getState() {
        if (state == null) {
            if (children == null) {
                return "open";
            } else {
                return "closed";
            }
        }
        return state ? "open" : "closed";
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public OrganizationNode(OrgNodeBean bean) {
        this.bean = bean;
    }

    public OrgNodeBean getAttributes() {
        return bean;
    }

    public Integer getId() {
        return bean.getOrgId();
    }

    public String getText() {
        return bean.getOrgName();
    }

    public void addChild(OrganizationNode org) {
        if (children == null) {
            children = new ArrayList<OrganizationNode>();
        }
        children.add(org);
    }

    public List<OrganizationNode> getChildren() {
        return children;
    }

    @Override
    public String toString() {
        String toString = "{id:'" + getId() + "', text:'" + getText() + "'";
        if (children != null && !children.isEmpty()) {
            toString += ", children:" + children;
        }
        return toString += "}";
    }

}
