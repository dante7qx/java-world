package org.java.world.dante.demo.icr;

import java.util.Map;

public class Result<T> {
	private Head head;
	private Map<String, Body<T>> body;

	public Head getHead() {
		return head;
	}

	public void setHead(Head head) {
		this.head = head;
	}

	public Map<String, Body<T>> getBody() {
		return body;
	}

	public void setBody(Map<String, Body<T>> body) {
		this.body = body;
	}

	
}
