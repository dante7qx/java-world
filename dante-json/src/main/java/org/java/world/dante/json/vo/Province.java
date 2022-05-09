package org.java.world.dante.json.vo;

import lombok.Data;

@Data
public class Province {
	private String name;
	
	public Province() {}

	public Province(String name) {
		this.name = name;
	}
	
	
}
