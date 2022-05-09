package org.java.world.distributeid.zookeeper;

public enum SequenceEnum {
	ORDER("order", "order"), ACCOUNT("account", "account");
	
	private String name;
	private String code;
	
	private SequenceEnum(String name, String code) {
		this.name = name;
		this.code = code;
	}
	
	public String getCode() {
		return this.code;
	}
	
	public String getName() {
		return name;
	}
}
