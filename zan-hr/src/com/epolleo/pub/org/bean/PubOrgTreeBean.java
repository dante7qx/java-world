package com.epolleo.pub.org.bean;

import java.util.ArrayList;
import java.util.List;

public class PubOrgTreeBean {
	private int id;
	private String text;
	private String state;
	private List<PubOrgTreeBean> children;
	private PubOrgTreeAttrBean attributes;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public List<PubOrgTreeBean> getChildren() {
		return children;
	}

	public void setChildren(List<PubOrgTreeBean> children) {
		this.children = children;
	}
	
	public void addChild(PubOrgTreeBean child) {
        if (children == null) {
            children = new ArrayList<PubOrgTreeBean>();
            children.add(child);
        } else {
            children.add(child);
        }
    }

	public PubOrgTreeAttrBean getAttributes() {
		return attributes;
	}

	public void setAttributes(PubOrgTreeAttrBean attributes) {
		this.attributes = attributes;
	}

	@Override
	public boolean equals(Object obj) {
		PubOrgTreeBean tree = (PubOrgTreeBean) obj;
		return id == tree.getId();
	}
}
