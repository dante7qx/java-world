package org.java.world.dante.json.vo;

import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import lombok.Data;

@Data
public class Country {
	
	private String code;
	private String name;
	
	@JacksonXmlElementWrapper(localName="provinces")
	@JacksonXmlProperty(localName="province")
	private List<Province> provinces;
	
	public Country() {
		
	}
		
	public Country(String code, String name, List<Province> provinces) {
		this.code = code;
		this.name = name;
		this.provinces = provinces;
	}
	
}
