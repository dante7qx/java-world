package com.dante.xml.vo;

public class A {

	private String x;
	private String y;

	public A() {
	}

	public A(String x, String y) {
		this.x = x;
		this.y = y;
	}

	public String getX() {
		return x;
	}

	public void setX(String x) {
		this.x = x;
	}

	public String getY() {
		return y;
	}

	public void setY(String y) {
		this.y = y;
	}

	@Override
	public String toString() {
		return "A [x=" + x + ", y=" + y + "]";
	}

}
