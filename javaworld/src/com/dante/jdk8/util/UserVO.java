package com.dante.jdk8.util;

import java.util.Objects;

public class UserVO {
	
	private int id;
	private String account;
	private String name;
	private int age;
	
	public UserVO() {
	}
	
	public UserVO(int id, String account, String name, int age) {
		this.id = id;
		this.account = account;
		this.name = name;
		this.age = age;
	}



	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
	@Override
	public String toString() {
		return "UserVO [id=" + id + ", account=" + account + ", name=" + name + ", age=" + age + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
		UserVO user = (UserVO) obj;
		return user.account.equals(this.account);
	}
	
	@Override
	public int hashCode() {
		return Objects.hashCode(this.account);
	}
	
}
