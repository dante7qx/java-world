package org.java.world.dante.serializable.vo;

import lombok.Data;

@Data
public class BaseResp<T> {
	private int success;
	private String errorCode;
	private Result<T> result;
	
}
