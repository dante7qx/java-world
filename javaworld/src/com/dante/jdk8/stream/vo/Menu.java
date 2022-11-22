package com.dante.jdk8.stream.vo;

import java.util.List;

public class Menu {
	
	private Integer id;
	private String name;
	private Integer parentId;
	private String extraProp;
	private List<Menu> children;

	public Menu(Integer id, String name, Integer parentId) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
    }

	public Menu(Integer id, String name, Integer parentId, String extraProp) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.extraProp = extraProp;
    }
	
    public Menu(Integer id, String name, Integer parentId, List<Menu> children) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.children = children;
    }
    
    

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public List<Menu> getChildren() {
		return children;
	}

	public void setChildren(List<Menu> children) {
		this.children = children;
	}
	
	public String getExtraProp() {
		return extraProp;
	}

	public void setExtraProp(String extraProp) {
		this.extraProp = extraProp;
	}

	@Override
	public String toString() {
		return "Menu [id=" + id + ", name=" + name + ", parentId=" + parentId + ", children=" + children + "]";
	}
	
    
}
