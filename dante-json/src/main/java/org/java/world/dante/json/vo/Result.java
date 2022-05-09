package org.java.world.dante.json.vo;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import lombok.Data;

@Data
public class Result<T> {

	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName="t")
	private T t;

}
