/*  
 * Copyright (c) 2012 epolleo.com
 * All rights reserved.
 * @author：cf
 * @date：2012-06-28 11:00:44 +0800
 * @version V1.0 
 */
package com.epolleo.bp.menu.bean;

import com.epolleo.bp.pub.BaseBean;

/**
 * 菜单表基本属性
 * 
 * @author cf
 * @date：2012-06-28 11:25:36 +0800
 */
public class Menu extends BaseBean {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -5818040372127176577L;
    private int menuId;
    private String name;
    private String url;
    private String funcCode;
    private String funcName;// 功能名称
    private int menuOrder;
    private int parentId;
    Int mid, pid;
    private int menuKind;
    private String menuInfo;
    private int height;
    private int width;
    private String backColor;
    private String showIn;
    private String target;

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

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

    public int getMenuOrder() {
        return menuOrder;
    }

    public void setMenuOrder(int menuOrder) {
        this.menuOrder = menuOrder;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getMid() {
        return String.valueOf(mid);
    }

    public String getPid() {
        return String.valueOf(pid);
    }

    public void sync() {
        mid = Int.getInt(menuId);
        pid = Int.getInt(parentId);
    }
    
    public int getMenuKind() {
		return menuKind;
	}

	public void setMenuKind(int menuKind) {
		this.menuKind = menuKind;
	}

	public String getMenuInfo() {
		return menuInfo;
	}

	public void setMenuInfo(String menuInfo) {
		this.menuInfo = menuInfo;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public String getBackColor() {
		return backColor;
	}

	public void setBackColor(String backColor) {
		this.backColor = backColor;
	}

	public String getShowIn() {
		return showIn;
	}

	public void setShowIn(String showIn) {
		this.showIn = showIn;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}
}
