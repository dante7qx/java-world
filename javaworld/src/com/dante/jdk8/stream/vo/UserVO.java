package com.dante.jdk8.stream.vo;

import java.io.Serializable;
import java.util.Objects;

public class UserVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String uniqueCode;
	private String name;
	private int age;

	public UserVO() {}

	public UserVO(Integer id, String name, int age, String uniqueCode) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
		this.uniqueCode = uniqueCode;
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

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getUniqueCode() {
		return uniqueCode;
	}

	public void setUniqueCode(String uniqueCode) {
		this.uniqueCode = uniqueCode;
	}

	@Override
	public int hashCode() {
		return Objects.hash(uniqueCode);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserVO other = (UserVO) obj;
		return Objects.equals(uniqueCode, other.uniqueCode);
	}

	@Override
	public String toString() {
		return "UserVO [id=" + id + ", uniqueCode=" + uniqueCode + ", name=" + name + ", age=" + age + "]";
	}
	
	
	
}
