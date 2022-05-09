package org.java.world.dante.serializable.vo;

import java.util.List;

import lombok.Data;

@Data
public class Country {
	
	private String code;
	private String name;
	
	private List<Province> provinces;
	
	public Country() {
		
	}
		
	public Country(String code, String name, List<Province> provinces) {
		this.code = code;
		this.name = name;
		this.provinces = provinces;
	}
	
}
