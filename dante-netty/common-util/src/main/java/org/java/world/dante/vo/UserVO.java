package org.java.world.dante.vo;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String uid;
	private String name;
	private Integer age;
	private Float salary;
	
	private List<ArticleVO> articles;
	
	public UserVO() {
		
	}

}
