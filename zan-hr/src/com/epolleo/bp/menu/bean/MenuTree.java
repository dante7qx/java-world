/*  
 * Copyright (c) 2012 epolleo.com
 * All rights reserved.
 * @author：cf
 * @date：2012-06-28 11:00:44 +0800
 * @version V1.0 
 */
package com.epolleo.bp.menu.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 构造菜单树对象
 * 
 * @author cf
 * @date：2012-06-28 11:25:55 +0800
 */
public class MenuTree {
    private String text;
    private int id;
    private String state;
    private List<MenuTree> children;
    // 存放菜单其他元素
    private Menu attributes;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<MenuTree> getChildren() {
        return children;
    }

    public void setChildren(List<MenuTree> children) {
        this.children = children;
    }

    public Menu getAttributes() {
        return attributes;
    }

    public void setAttributes(Menu attributes) {
        this.attributes = attributes;
    }

    @Override
    public boolean equals(Object obj) {
        MenuTree tree = (MenuTree) obj;
        return tree.getId() == this.getId();
    }

    public void addChild(MenuTree menu) {
        if (children == null) {
            children = new ArrayList<MenuTree>();
            children.add(menu);
        } else {
            children.add(menu);
        }
    }

    public boolean hasChild() {
        return children != null && children.size() > 0;
    }

    public String toString() {
        return "Menu[id=" + id + ", pid=" + attributes.getParentId() + ", "
            + text + "]";
    }

}
