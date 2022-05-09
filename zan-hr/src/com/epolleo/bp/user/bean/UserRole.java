/*  
 * Copyright (c) 2012 epolleo.com
 * All rights reserved.
 *
 * @date：2012-06-28 11:00:44 +0800
 * @version V1.0 
 */
package com.epolleo.bp.user.bean;

import com.epolleo.bp.role.bean.RoleBean;

/**
 * 用户角色关系bean
 */
public class UserRole extends RoleBean {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 6169898345535653975L;
    private String userId;
    // 用于标示是否属于该角色
    private boolean checked;

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean isCheck) {
        this.checked = isCheck;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
