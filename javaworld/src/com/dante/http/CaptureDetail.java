package com.dante.http;

public class CaptureDetail {
	private String address;
	private String tel;
	private String imgUrl;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTel() {
		return tel != null ? tel : "";
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	
	@Override
	public String toString() {
		return address + "==" + tel + "==" + imgUrl;
	}
}
