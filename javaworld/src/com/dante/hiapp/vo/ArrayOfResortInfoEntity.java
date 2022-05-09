package com.dante.hiapp.vo;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName="arrayOfResortInfoEntity")
public class ArrayOfResortInfoEntity {

	private ResortInfoEntity resortInfoEntity;

	public ResortInfoEntity getResortInfoEntity() {
		return resortInfoEntity;
	}

	public void setResortInfoEntity(ResortInfoEntity resortInfoEntity) {
		this.resortInfoEntity = resortInfoEntity;
	}

	@Override
	public String toString() {
		return "ArrayOfResortInfoEntity [resortInfoEntity=" + resortInfoEntity + "]";
	}

}
