package com.epolleo.bp.role.bean;

import java.util.List;

/**
 * FunctionRoleTree
 * <p>
 * Date: 2012-12-20,18:47:05 +0800
 * 
 * @author Song Sun
 * @version 1.0
 */
public class FunctionRoleTree {
    private String text;
    private String id;
    private String state;
    private String parentId;
    private Boolean checked;

    private OtherAttribute attributes;
    private List<FunctionRoleTree> children;

    public List<FunctionRoleTree> getChildren() {
        return children;
    }

    public void setChildren(List<FunctionRoleTree> children) {
        this.children = children;
    }

    public void setBasicInfo(FunctionRole basicInfo) {
        text = basicInfo.getFuncName();
        id = basicInfo.getFuncCode();
        state = "open";
        checked = basicInfo.isSelected();
        parentId = basicInfo.getParent();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getState() {
        if (children == null) {
            return "open";
        } else {
            return "closed";
        }
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public OtherAttribute getAttributes() {
        if (attributes == null) {
            attributes = new OtherAttribute();
            if (children == null) {
                attributes.setHasChildren(false);
            } else {
                attributes.setHasChildren(true);
            }

        }
        return attributes;
    }

    public void setAttributes(OtherAttribute attributes) {
        this.attributes = attributes;
    }

}
