package org.java.world.mapstruct.model;

import java.util.Date;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain=true)
public class CarPO {
	private Long id;
	private String name;
	private String fullSign;
	private int year;
	private Date updateDate;
	
}
