package org.java.world.mapstruct.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class SubVO1 extends BaseVO {
	
	private String name;

}
