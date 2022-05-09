package com.dante.hiapp.vo;

public class HnaResponse<T> {

	private Integer success;

	private Integer errorCode;

	private String errorInfo;

	private T result;

	public Integer getSuccess() {
		return success;
	}

	public void setSuccess(Integer success) {
		this.success = success;
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorInfo() {
		return errorInfo;
	}

	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "HnaResponse [success=" + success + ", errorCode=" + errorCode + ", errorInfo=" + errorInfo + ", result="
				+ result + "]";
	}

}
