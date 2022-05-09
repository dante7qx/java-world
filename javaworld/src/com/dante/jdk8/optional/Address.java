package com.dante.jdk8.optional;

public class Address {
	
	private String area;
	private Country country;
	
	public Address() {
	}
	
	public Address(String area, Country country) {
		this.area = area;
		this.country = country;
	}

	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public Country getCountry() {
		return country;
	}
	public void setCountry(Country country) {
		this.country = country;
	}

	@Override
	public String toString() {
		return "Address [area=" + area + ", country=" + country + "]";
	}
	
}
