/*-
 * Copyright (c) 2012 epolleo.com
 * All rights reserved. 
 * RoleAction.java
 * Date: 2012-06-29 15:10:31 +0800 
 */
package com.epolleo.bp.role.bean;

import com.epolleo.bp.pub.BaseBean;

/**
 * RoleBean.java
 */
public class RoleBean extends BaseBean {
	/**
	 * serialVersionUID
	 */
	static final long serialVersionUID = 8420655654743383822L;
	int id;
	String name;
	String descr;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescribe() {
		return descr;
	}

	public void setDescribe(String descr) {
		this.descr = descr;
	}

}
