package org.java.world.dante.serializable.vo;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class UserVO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String name;
	private Date birthday;
	@NonNull
	private String sex;
	
	
	public UserVO() {}
	
	
}
