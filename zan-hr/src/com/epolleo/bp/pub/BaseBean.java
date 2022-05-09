/**
 * Copyright (c) 2012 epolleo.com
 * All rights reserved. 
 * BaseEntity.java
 * Date: 2012-6-28
 */
package com.epolleo.bp.pub;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 数据实体公共属性
 * </p>
 * 
 * Date: 2012-6-28 下午7:20:57
 * 
 * @author jack
 * @version 1.0
 */
public abstract class BaseBean implements Serializable {

    private static final long serialVersionUID = -7595979157245031743L;

    protected String createUser;
    protected Date createTime;
    protected String updateUser;
    protected Date updateTime;

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

}
