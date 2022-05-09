package org.java.world.dante.vo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "user")
public class UserVO implements Serializable {
	private static final long serialVersionUID = 988964688214186963L;

	private String id;
	private String account;
	
	// 必须具有无参构造方法
	public UserVO() {
	}
	
	public UserVO(String id, String account) {
		this.id = id;
		this.account = account;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

}
