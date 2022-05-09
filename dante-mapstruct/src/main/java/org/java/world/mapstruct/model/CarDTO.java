package org.java.world.mapstruct.model;

import lombok.Data;

@Data
public class CarDTO {
	
	private Long id;
	private String name;
	private String sign;
	private int year;
	private String manufacturer;
	private String updateDate;

}
