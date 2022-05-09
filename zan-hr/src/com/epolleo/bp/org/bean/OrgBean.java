/*-
 * Copyright (c) 2012 epolleo.com
 * All rights reserved.
 */
package com.epolleo.bp.org.bean;

import java.util.List;

import com.epolleo.bp.pub.BaseBean;

/**
 * 组织机构bean
 */
public class OrgBean extends BaseBean {

    private static final long serialVersionUID = 3277418748810693720L;

    private Integer orgId;
    private String orgName;
    private String orgCode;
    private String fullId;
    private String fullCode;
    private String shortName;
    private String fullName;
    private boolean enable;
    private String orgPinYin;
    private String orgType;
    private Integer orgTypeId;
    private Integer parentId;
    private int showOrder;
    private Integer locId;
    private List<OrgBean> children;

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public int getShowOrder() {
        return showOrder;
    }

    public void setShowOrder(int showOrder) {
        this.showOrder = showOrder;
    }

    public List<OrgBean> getChildren() {
        return children;
    }

    public void setChildren(List<OrgBean> children) {
        this.children = children;
    }

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgPinYin() {
        return orgPinYin;
    }

    public void setOrgPinYin(String orgPinYin) {
        this.orgPinYin = orgPinYin;
    }


    public void setFullId(String fullId) {
        this.fullId = fullId;
    }

    public String getFullCode() {
        return fullCode;
    }

    public void setFullCode(String fullCode) {
        this.fullCode = fullCode;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public Integer getOrgTypeId() {
        return orgTypeId;
    }

    public void setOrgTypeId(Integer orgTypeId) {
        this.orgTypeId = orgTypeId;
    }

    public String getFullId() {
        return fullId;
    }

    public Integer getLocId() {
		return locId;
	}

	public void setLocId(Integer locId) {
		this.locId = locId;
	}

	@Override
    public String toString() {
        return "OrgBean [orgId=" + orgId + ", orgName=" + orgName
            + ", orgCode=" + orgCode + ", fullId=" + fullId + ", fullCode="
            + fullCode + ", shortName=" + shortName + ", fullName=" + fullName
            + ", enable=" + enable + ", orgPinYin=" + orgPinYin + ", orgType="
            + orgType + ", orgTypeId=" + orgTypeId + ", parentId=" + parentId
            + ", showOrder=" + showOrder + ", locId=" + locId + ", children=" + children + "]";
    }

}
