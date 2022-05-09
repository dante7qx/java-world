package com.dante.xml.vo;

import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

public class ResA {

	@JacksonXmlElementWrapper(useWrapping=false)
	private List<A> a;

	public List<A> getA() {
		return a;
	}

	public void setA(List<A> a) {
		this.a = a;
	}

	@Override
	public String toString() {
		return "ResA [a=" + a + "]";
	}

}
