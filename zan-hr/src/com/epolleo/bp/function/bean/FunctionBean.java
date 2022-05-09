/*-
 * Copyright (c) 2012 epolleo.com
 * All rights reserved.
 */
package com.epolleo.bp.function.bean;

import com.epolleo.bp.pub.BaseBean;

/**
 * FunctionBean
 * <p>
 * Date: 2012-07-10,15:35:16 +0800
 * 
 * @version 1.0
 */
public class FunctionBean extends BaseBean {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 9177743471520870262L;
    private String funcCode;
    private String funcName;
    private boolean state;
    private int funcOrder;
    private String parent;
    private String funcNote;

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

    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public int getFuncOrder() {
        return funcOrder;
    }

    public void setFuncOrder(int funcOrder) {
        this.funcOrder = funcOrder;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getFuncNote() {
        return funcNote;
    }

    public void setFuncNote(String funcNote) {
        this.funcNote = funcNote;
    }

    @Override
    public String toString() {
        return "FunctionBean [funcCode=" + funcCode + ", funcName=" + funcName
            + ", state=" + state + ", funcOrder=" + funcOrder + ", parent="
            + parent + ", funcNote=" + funcNote + ", createUser=" + createUser
            + ", createTime=" + createTime + ", updateUser=" + updateUser
            + ", updateTime=" + updateTime + "]";
    }

}
