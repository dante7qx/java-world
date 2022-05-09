package com.dante.hiapp.vo.req;

public class HnaRequest<T> {

	private String rsaKey;
	private String rsaCode;
	private String code;
	private String codePwd;
	private String routeCode;
	private Integer messageFormatType = 0;
	private Integer compressDataType = 0;
	private Paramter<T> paramter = new Paramter<>();

	public String getRsaKey() {
		return rsaKey;
	}

	public void setRsaKey(String rsaKey) {
		this.rsaKey = rsaKey;
	}

	public String getRsaCode() {
		return rsaCode;
	}

	public void setRsaCode(String rsaCode) {
		this.rsaCode = rsaCode;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCodePwd() {
		return codePwd;
	}

	public void setCodePwd(String codePwd) {
		this.codePwd = codePwd;
	}

	public String getRouteCode() {
		return routeCode;
	}

	public void setRouteCode(String routeCode) {
		this.routeCode = routeCode;
	}

	public Integer getMessageFormatType() {
		return messageFormatType;
	}

	public void setMessageFormatType(Integer messageFormatType) {
		this.messageFormatType = messageFormatType;
	}

	public Integer getCompressDataType() {
		return compressDataType;
	}

	public void setCompressDataType(Integer compressDataType) {
		this.compressDataType = compressDataType;
	}

	public Paramter<T> getParamter() {
		return paramter;
	}

	public void setParamter(Paramter<T> paramter) {
		this.paramter = paramter;
	}

}
