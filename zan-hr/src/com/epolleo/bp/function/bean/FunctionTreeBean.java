/*-
 * Copyright (c) 2012 epolleo.com
 * All rights reserved.
 */
package com.epolleo.bp.function.bean;

import java.io.Serializable;
import java.util.List;

/**
 * FunctionTreeBean
 * <p>
 * Date: 2012-07-10,15:35:36 +0800
 * 
 * @version 1.0
 */
public class FunctionTreeBean implements Serializable {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -9072407964710840404L;
    private String id;
    private String iconCls;
    private String text;
    private boolean state;
    private List<FunctionTreeBean> children;
    private FuncTreeAttributeBean attributes;
    

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getState() {
        return state ? "open" : "closed";
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public List<FunctionTreeBean> getChildren() {
        return children;
    }

    public void setChildren(List<FunctionTreeBean> children) {
        this.children = children;
    }
    
    public FuncTreeAttributeBean getAttributes() {
        return attributes;
    }

    public void setAttributes(FuncTreeAttributeBean attributes) {
        this.attributes = attributes;
    }
}
