/*-
 * Copyright (c) 2012 epolleo.com
 * All rights reserved.
 */
package com.epolleo.bp.org.bean;

import com.epolleo.bp.pub.BaseBean;

/**
 * 组织结构类型bean
 * @author sky
 * @date：2012-07-12 10:47:20 +0800
 */
public class OrgTypeBean extends BaseBean {

    private static final long serialVersionUID = 4541203255517594058L;

    private String id;
    private String orgTypeId;
    private String orgType;

    public String getId() {
        return id;
    }

    public String getOrgTypeId() {
        return orgTypeId;
    }

    public void setOrgTypeId(String orgTypeId) {
        this.orgTypeId = orgTypeId;
        this.id = orgTypeId;
    }

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    @Override
    public String toString() {
        return "OrgTypeBean [id=" + id + ", orgTypeId=" + orgTypeId
                + ", orgType=" + orgType + ", createUser=" + createUser
                + ", createTime=" + createTime + ", updateUser=" + updateUser
                + ", updateTime=" + updateTime + "]";
    }

}
