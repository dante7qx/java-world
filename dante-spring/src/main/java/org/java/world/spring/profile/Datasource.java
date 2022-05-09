package org.java.world.spring.profile;


public class Datasource {
	private String user;
	private String pwd;

	public Datasource(String user, String pwd) {
		this.user = user;
		this.pwd = pwd;
	}

	@Override
	public String toString() {
		return "[user: " + user + ", pwd:" + pwd + "]";
	}
}
