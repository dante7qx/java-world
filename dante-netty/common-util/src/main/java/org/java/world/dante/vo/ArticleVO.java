package org.java.world.dante.vo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ArticleVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String title;
	private String author;
	
	public ArticleVO() {}
	
}
