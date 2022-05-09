package com.dante.jdk8.optional;

public class Country {
	
	private String cnName;
	private String isoCode;
	
	public Country() {
	}
	
	
	public Country(String cnName, String isoCode) {
		this.cnName = cnName;
		this.isoCode = isoCode;
	}

	public String getCnName() {
		return cnName;
	}
	public void setCnName(String cnName) {
		this.cnName = cnName;
	}
	public String getIsoCode() {
		return isoCode;
	}
	public void setIsoCode(String isoCode) {
		this.isoCode = isoCode;
	}

	@Override
	public String toString() {
		return "Country [cnName=" + cnName + ", isoCode=" + isoCode + "]";
	}
	
	
}
