package com.dante.hiapp.vo.req;

public class Paramter<T> {
	public Paramter() {
	}
	
	public Paramter(T t) {
		this.t = t;
	}
	
	private T t;

	public T getT() {
		return t;
	}

	public void setT(T t) {
		this.t = t;
	}

}
