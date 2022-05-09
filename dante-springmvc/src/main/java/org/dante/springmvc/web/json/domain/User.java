package org.dante.springmvc.web.json.domain;

/**
 * jackson对对象和json做转换时一定需要空构造
 * 
 * @author dante
 *
 */
public class User {
	private Integer id;
	private String name;

	public User() {
		super();
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

	@Override
	public String toString() {
		return "[id:" + id + ", name:" + name + "]";
	}
}
