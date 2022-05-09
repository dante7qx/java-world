package org.java.world.dante.json.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.NonNull;

@Data
public class UserVO {
	private Integer id;
	private String name;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
	private Date birthday;
	@NonNull
	private String sex;
	
	/*
	@JsonSerialize(using = CustomDateSerializer.class)  
	public Date getBirthday() {
		return this.birthday;
	}
	
	@JsonDeserialize(using = CustomJsonDateDeserializer.class) 
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	*/
}
