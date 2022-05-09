package org.dante.springmvc.mybatis.domain;

import java.util.Set;

public class Role {
	private Integer id;
	private String name;
	private String remark;
	private Set<Integer> permissionIds;

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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Set<Integer> getPermissionIds() {
		return permissionIds;
	}

	public void setPermissionIds(Set<Integer> permissionIds) {
		this.permissionIds = permissionIds;
	}


}
