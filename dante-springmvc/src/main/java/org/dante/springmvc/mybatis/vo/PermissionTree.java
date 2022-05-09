package org.dante.springmvc.mybatis.vo;

import java.util.ArrayList;
import java.util.List;

public class PermissionTree {
	private Integer id;
	private Integer pid;
	private String name;
	private String text;
	private String code;
	private boolean open;
	
	private List<PermissionTree> children;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<PermissionTree> getChildren() {
		if(this.children == null) {
			this.children = new ArrayList<PermissionTree>();
		}
		return children;
	}
	
}
