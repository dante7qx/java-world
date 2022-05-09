package com.dante.jdair.vo;

import java.util.ArrayList;
import java.util.List;

public class ProductVo {
	private String dealCode;
	private String name;
	private String imgUrl;
	private String currentPrice;
	private String pursePrice;

	private List<ProductDetailVo> details;
	
	public String getDealCode() {
		return dealCode;
	}
	
	public void setDealCode(String dealCode) {
		this.dealCode = dealCode;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(String currentPrice) {
		this.currentPrice = currentPrice;
	}

	public String getPursePrice() {
		return pursePrice;
	}

	public void setPursePrice(String pursePrice) {
		this.pursePrice = pursePrice;
	}

	public List<ProductDetailVo> getDetails() {
		if(this.details == null) {
			this.details = new ArrayList<ProductDetailVo>();
		}
		return details;
	}

}
