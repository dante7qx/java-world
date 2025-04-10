package com.dante.jdk8.stream.vo;

import com.dante.jdk8.stream.annotation.Author;

@Author(name = "dante")
public class Person {
	private Long id;
	private String name;
	private int age;
	
	public Person() {
	}
	
	public Person(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	@Override
	public String toString() {
		return "{id=" + id + ", name=" + name + ", age=" + age + "}";
	}

}
